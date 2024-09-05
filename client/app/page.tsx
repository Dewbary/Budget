import BudgetAmount from "@/components/BudgetAmount";
import Category from "@/components/Category";
import { PlaidSetUp } from "@/components/PlaidSetUp";
import TransactionList from "@/components/TransactionList";

export default async function Home() {
  const response = await fetch("http://localhost:8080/hello?name=Brendan");
  const data = await response.text();

  const accountBalancesResponse = await fetch("http://localhost:8080/run");
  const accountBalances: any[] = await accountBalancesResponse.json();

  console.log(accountBalances);

  return (
    <main className="flex min-h-screen flex-col items-center justify-center p-24">
      <div className="mb-8 bg-slate-200 p-24 rounded-3xl">
        <div className="text-xl font-bold mb-4">Monthly Budget</div>
        <BudgetAmount id="month" />
      </div>
      <PlaidSetUp />

      {data}
      <div>
        Account Balances
        {accountBalances.map((account, index) => (
          <div key={index} className="flex justify-between">
            <div>{account.available}</div>
            <div>{account.current}</div>
          </div>
        ))}
      </div>
      <div className="flex">
        <Category id="Groceries" />
        <Category id="Utilities" />
        <Category id="Savings" />
        <Category id="Car" />
        <Category id="Fun" />
      </div>
      <div className="mt-8">
        <TransactionList />
      </div>
    </main>
  );
}
