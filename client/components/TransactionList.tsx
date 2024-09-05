type Props = {};

const TransactionList = ({}: Props) => {
  return (
    <div className="py-8 px-8 w-[800px] bg-slate-600 text-white rounded-3xl">
      <div className="text-xl font-bold mb-4">Transactions</div>
      <div>Transaction 1: $10.99</div>
      <div>Transaction 2: $11.99</div>
      <div>Transaction 3: $12.99</div>
      <div>Transaction 4: $13.99</div>
      <div>Transaction 5: $14.99</div>
      <div>Transaction 6: $15.99</div>
      <div>Transaction 7: $16.99</div>
      <div>Transaction 8: $17.99</div>
      <div>Transaction 9: $18.99</div>
    </div>
  );
};

export default TransactionList;
