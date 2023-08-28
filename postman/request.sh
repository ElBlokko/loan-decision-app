curl -X POST "http://localhost:8080/loan-decision/check" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "personalCode=49002010976" \
  -d "loanAmount=3000" \
  -d "loanPeriod=24"
