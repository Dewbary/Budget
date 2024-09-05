import React from "react";
import BudgetAmount from "./BudgetAmount";

type Props = {
  id: string;
};

const Category = ({ id }: Props) => {
  return (
    <div className="flex flex-col justify-center items-center m-4 bg-slate-100 p-16 rounded-3xl">
      <div className="mb-4 font-bold text-xl">{id}</div>
      <BudgetAmount id={id} />
    </div>
  );
};

export default Category;
