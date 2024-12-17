import React from "react";
import Skeleton, { SkeletonTheme } from "react-loading-skeleton";
import "react-loading-skeleton/dist/skeleton.css";
import { formatPrice } from "../../utils/formatPrice";

const TransactionTable = ({ transactions, onDetail, isBusy }) => {
  const formatDate = (dateString) => {
    const date = new Date(dateString);
    const options = { year: "numeric", month: "long", day: "numeric" };
    return date.toLocaleDateString("id-ID", options);
  };
  return (
    <SkeletonTheme baseColor="#d1d1d1" highlightColor="#aaaaaa">
      <div className="overflow-x-auto">
        <table className="w-full min-w-[800px]">
          <thead>
            <tr className="text-left border-b bg-gray-50">
              <th className="px-4 py-3 text-sm font-semibold">TANGGAL</th>
              <th className="px-4 py-3 text-sm font-semibold">NAMA PENGUNJUNG</th>
              <th className="px-4 py-3 text-sm font-semibold">JENIS TIKET</th>
              <th className="px-4 py-3 text-sm font-semibold">NOMINAL</th>
              <th className="px-4 py-3 text-sm font-semibold">STATUS</th>
              <th className="px-4 py-3 text-sm font-semibold">DETAIL</th>
            </tr>
          </thead>
          <tbody>
            {isBusy()
              ? Array(3)
                .fill()
                .map((_, index) => (
                  <tr key={index} className="border-b">
                    <td className="px-4 py-4 text-sm">
                      <Skeleton width="60%" />
                    </td>
                    <td className="px-4 py-4 text-sm">
                      <Skeleton width="50%" />
                    </td>
                    <td className="px-4 py-4 text-sm">
                      <Skeleton width="30%" />
                    </td>
                    <td className="px-4 py-4 text-sm">
                      <Skeleton width="40%" />
                    </td>
                    <td className="px-4 py-4 text-sm">
                      <Skeleton width="70%" />
                    </td>
                    <td className="px-4 py-4">
                      <Skeleton width="60%" />
                    </td>
                  </tr>
                ))
              : transactions.map((transaction) => (
                <tr key={transaction.transaction_id} className="border-b">
                    <td className="px-4 py-4 text-sm">{formatDate(transaction.transaction_date)}</td>
                    <td className="px-4 py-4 text-sm">{transaction.username}</td>
                  <td className="px-4 py-4">
                    <span className={`text-sm px-3 py-1 rounded-full ${transaction.ticket_name.includes('Tiket')
                        ? 'bg-yellow-50 text-yellow-700'
                        : 'bg-blue-50 text-blue-700'
                      }`}>
                      {transaction.ticket_name}
                    </span>
                  </td>
                  <td className="px-4 py-4 text-sm">{transaction.ticket_name}</td>
                  <td className="px-4 py-4 text-sm">{formatPrice(transaction.ticket_price)}</td>
                  <td className="px-4 py-4 text-sm">
                    <span className="px-3 py-1 text-sm text-white bg-green-600 rounded-md">
                      {transaction.transaction_status}
                    </span>
                  </td>
                  <td className="px-4 py-4 text-sm">
                    <button className="bg-blue-600 text-white px-4 py-1.5 rounded-md text-sm flex items-center gap-2"
                      onClick={() => onDetail(transaction.transaction_id)}>
                      Detail
                      <svg className="w-4 h-4" viewBox="0 0 24 24" fill="none">
                        <path d="M9 5l7 7-7 7" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" />
                      </svg>
                    </button>
                  </td>
                </tr>
              ))}
          </tbody>
        </table>
      </div>
    </SkeletonTheme>
  );
};

export default TransactionTable;
