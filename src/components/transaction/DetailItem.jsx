import React from "react";

const DetailItem = ({ label, value, isStatus }) => {
  return (
    <div className="flex flex-col gap-1 py-4 border-b md:flex-row md:justify-between md:items-center md:gap-0">
      <span className="text-gray-600">{label}</span>
      {isStatus ? (
        <span className="px-6 py-2 text-center text-white bg-teal-500 rounded-lg">{value}</span>
      ) : (
        <span>{value}</span>
      )}
    </div>
  );
};



export default DetailItem;
