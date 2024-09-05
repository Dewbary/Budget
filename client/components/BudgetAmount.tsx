type Props = {
  id: string;
};

const BudgetAmount = ({ id }: Props) => {
  return (
    <div className="flex flex-col items-center">
      <div>Total: $4000</div>
      <div>Remaining: $1000</div>
    </div>
  );
};

export default BudgetAmount;
