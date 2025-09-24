OpenRewrite runner

This repository includes a helper script at `scripts/run-openrewrite.sh` to run the OpenRewrite CLI for automated code upgrades.

Usage (dry-run):

```bash
# from repo root
chmod +x scripts/run-openrewrite.sh
scripts/run-openrewrite.sh
```

To apply changes (creates a backup of `src/` under `.rewrite/backup`):

```bash
DRY_RUN=0 scripts/run-openrewrite.sh
```

To override recipe (example):

```bash
UPGRADE_RECIPE=org.openrewrite.java.migrate.UpgradeJavaVersion DRY_RUN=0 scripts/run-openrewrite.sh
```

Notes:
- The script requires Java to be installed. It prefers Java 21 if available via `/usr/libexec/java_home -v 21`.
- If automatic metadata lookup fails, set `REWRITE_VERSION` to a known rewrite-cli version.
