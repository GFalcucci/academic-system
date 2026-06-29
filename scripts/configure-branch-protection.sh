#!/bin/bash
# TUS-2420 - Configure branch protection for pull requests
#
# Pré-requisitos:
#   - GitHub CLI instalado e autenticado: gh auth login
#
# Uso:
#   chmod +x scripts/configure-branch-protection.sh
#   ./scripts/configure-branch-protection.sh

OWNER="${GITHUB_OWNER:-seu-usuario-github}"
REPO="${GITHUB_REPO:-academic-system-semester-assignment-2026}"
BRANCH="main"

echo "Configurando branch protection em $OWNER/$REPO no branch '$BRANCH'..."

gh api \
  --method PUT \
  -H "Accept: application/vnd.github+json" \
  "/repos/$OWNER/$REPO/branches/$BRANCH/protection" \
  --input - << 'JSON'
{
  "required_status_checks": {
    "strict": true,
    "contexts": ["build-and-test"]
  },
  "enforce_admins": false,
  "required_pull_request_reviews": {
    "required_approving_review_count": 1,
    "dismiss_stale_reviews": true
  },
  "restrictions": null,
  "allow_force_pushes": false,
  "allow_deletions": false
}
JSON

if [ $? -eq 0 ]; then
  echo ""
  echo "Branch protection configurado com sucesso!"
  echo ""
  echo "Regras aplicadas ao branch '$BRANCH':"
  echo "  [AC2] Pushes diretos: PROIBIDOS"
  echo "  [AC3] Mudancas apenas via Pull Request"
  echo "  [AC4] Workflow 'build-and-test' deve passar antes do merge"
  echo "  [AC5] PRs com build quebrado nao podem ser mergeados"
  echo "  [AC6] PRs com testes falhando nao podem ser mergeados"
  echo "  [AC9] Branch permanece deployavel apos cada merge"
else
  echo "Erro ao configurar branch protection."
  exit 1
fi
