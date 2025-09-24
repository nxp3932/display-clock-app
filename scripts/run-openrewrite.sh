#!/usr/bin/env bash
set -euo pipefail

# Lightweight OpenRewrite runner for this repo.
# - Downloads the rewrite-cli jar to .rewrite/
# - Performs a dry-run by default
# - To apply changes set DRY_RUN=0
# - You can override the recipe with UPGRADE_RECIPE env var

REPO_ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$REPO_ROOT"

echo "OpenRewrite runner — repository: $REPO_ROOT"

# Locate Java 21 if available
if [ -z "${JAVA_HOME-}" ]; then
  if command -v /usr/libexec/java_home >/dev/null 2>&1; then
    JAVA_HOME=$(/usr/libexec/java_home -v 21 2>/dev/null || /usr/libexec/java_home)
  else
    JAVA_HOME=$(dirname "$(dirname "$(command -v java 2>/dev/null || true)")")
  fi
fi

export JAVA_HOME
export PATH="$JAVA_HOME/bin:$PATH"

echo "Using JAVA_HOME=$JAVA_HOME"

RELEASE=""
if [ -n "${REWRITE_VERSION-}" ]; then
  RELEASE="$REWRITE_VERSION"
  echo "Using REWRITE_VERSION=$RELEASE from environment"
else
  METADATA_URL="https://repo1.maven.org/maven2/org/openrewrite/rewrite-cli/maven-metadata.xml"
  echo "Fetching latest OpenRewrite CLI version metadata..."
  METADATA=$(curl -fsSL "$METADATA_URL") || METADATA=""

  if [ -n "$METADATA" ]; then
    RELEASE=$(printf "%s" "$METADATA" | sed -n 's:.*<release>\(.*\)</release>.*:\1:p') || true
  fi
  if [ -z "$RELEASE" ] && [ -n "$METADATA" ]; then
    RELEASE=$(printf "%s" "$METADATA" | sed -n 's:.*<version>\(.*\)</version>.*:\1:p' | tail -n1) || true
  fi

  if [ -z "$RELEASE" ]; then
    echo "Could not determine latest rewrite-cli version automatically.\nPlease set REWRITE_VERSION env var to a known version (e.g. 8.40.0)."
    exit 1
  fi
fi

JAR_DIR="$REPO_ROOT/.rewrite"
mkdir -p "$JAR_DIR"
JAR_FILE="$JAR_DIR/rewrite-cli-$RELEASE-all.jar"

if [ ! -f "$JAR_FILE" ]; then
  echo "Downloading rewrite-cli version $RELEASE to $JAR_FILE ..."
  curl -fsSL -o "$JAR_FILE" "https://repo1.maven.org/maven2/org/openrewrite/rewrite-cli/$RELEASE/rewrite-cli-$RELEASE-all.jar"
  echo "Downloaded $JAR_FILE"
else
  echo "Using cached $JAR_FILE"
fi

# Default recipe — adjust if necessary
UPGRADE_RECIPE="${UPGRADE_RECIPE:-org.openrewrite.java.migrate.UpgradeJavaVersion}"
DRY_RUN="${DRY_RUN:-1}"

echo
echo "Planned OpenRewrite recipe: $UPGRADE_RECIPE"
echo "Dry-run mode: $DRY_RUN (1 = dry-run, 0 = apply)"
echo

CMD="$JAVA_HOME/bin/java -jar $JAR_FILE -d . --recipes $UPGRADE_RECIPE"

echo "Planned command:" 
echo "$CMD"
echo
echo "NOTE: This script does a dry-run by default. To apply changes, rerun with DRY_RUN=0."

if [ "$DRY_RUN" -eq 1 ]; then
  echo "Running dry-run..."
  "$JAVA_HOME/bin/java" -jar "$JAR_FILE" -d . --recipes "$UPGRADE_RECIPE" --dryRun || true
  echo "Dry-run finished. No files were modified. Review CLI output above."
else
  echo "Applying changes — creating a backup of src/ under .rewrite/backup"
  mkdir -p .rewrite/backup
  ts=$(date +%Y%m%d%H%M%S)
  cp -a src ".rewrite/backup/src.$ts"
  "$JAVA_HOME/bin/java" -jar "$JAR_FILE" -d . --recipes "$UPGRADE_RECIPE"
  echo "OpenRewrite run finished. Inspect changes with 'git status' and 'git diff'."
fi

exit 0
