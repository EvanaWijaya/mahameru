import React, { useState } from "react";
import PageHeader from "../components/PageHeader";
import Pagination from "../components/Pagination";
import TransactionTable from "../components/transaction/TransactionTable";
import useTransactionHook from "../hooks/useTransactionHook";
import SearchBar from "../components/SearchBar";
import { useNavigate } from "react-router-dom";

const TransactionData = () => {
  const navigate = useNavigate();
  const {
    transactions,
    page,
    setPage,
    total,
    totalPages,
    setQuery,
    isBusy,
    filterQuery,
    setFilterQuery,
    handleFilterTransactions,
  } = useTransactionHook();

  const [isFilterOpen, setIsFilterOpen] = useState(false);
  const [activeFilter, setActiveFilter] = useState(null);

  const handleFilterSelect = async (filterKey) => {
    setFilterQuery(filterKey);  
  
    setActiveFilter(filterKey);
    setIsFilterOpen(false);
    await handleFilterTransactions(filterKey); 
  };
  

  const detailTransactionRoute = (id) => {
    navigate(`/detail-transaksi/${id}`);
  };

  return (
    <div className="flex-1 min-h-screen p-4 py-10 bg-gray-100 lg:ml-64">
      <PageHeader
        title="Data Pengunjung"
        subtitle="Ini adalah halaman untuk melihat data transaksi Agrowisata Tepas Papandayan."
        icon={
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            strokeWidth={1.5}
            stroke="currentColor"
            className="w-5 h-5"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              d="M16.023 9.348h4.992v-.001M2.985 19.644v-4.992m0 0h4.992m-4.993 0 3.181 3.183a8.25 8.25 0 0 0 13.803-3.7M4.031 9.865a8.25 8.25 0 0 1 13.803-3.7l3.181 3.182m0-4.991v4.99"
            />
          </svg>
        }
      />
      <div className="overflow-hidden bg-white rounded-lg shadow-sm">
        <div className="flex flex-col items-start justify-between gap-4 p-4 lg:flex-row lg:items-center">
          <div>
            <h3 className="text-lg font-semibold">Riwayat Transaksi</h3>
            <p className="text-sm text-gray-600">
              Total transaksi saat ini: <span className="font-medium">{total}</span>
            </p>
          </div>
          <div className="relative flex flex-col w-full gap-2 sm:flex-row lg:w-auto">
            <button
              onClick={() => setIsFilterOpen((prev) => !prev)}
              className="flex items-center justify-center gap-2 px-4 py-2 text-sm rounded-lg bg-gray-50"
            >
              <svg
                className="w-4 h-4"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth={2}
                  d="M3 4a1 1 0 011-1h16a1 1 0 011 1v2.586a1 1 0 01-.293.707l-6.414 6.414a1 1 0 00-.293.707V17l-4 4v-6.586a1 1 0 00-.293-.707L3.293 7.293A1 1 0 013 6.586V4z"
                />
              </svg>
              Filter
            </button>
            {isFilterOpen && (
              <div className="absolute z-10 w-48 p-2 mt-2 bg-white border rounded shadow-lg">
                <button
                  className={`block w-full px-4 py-2 text-sm text-left text-gray-700 hover:bg-gray-100 ${activeFilter === "start_date" ? "bg-[#00796B] text-white" : ""}`}
                  onClick={() => handleFilterSelect("start_date")}
                >
                  Terlama
                </button>
                <button
                  className={`block w-full px-4 py-2 text-sm text-left text-gray-700 hover:bg-gray-100 ${activeFilter === "end_date" ? "bg-[#00796B] text-white" : ""}`}
                  onClick={() => handleFilterSelect("end_date")}
                >
                  Terbaru
                </button>
              </div>
            )}
            <SearchBar
              onQueryChange={(query) => setQuery(query)}
              onSearch={(e) => {
                e.preventDefault();
                setPage(1);
              }}
            />
          </div>
        </div>
        {total === 0 && !isBusy() ? (
          <div className="p-4 text-center text-gray-600">
            <p>Tidak ada data transaksi saat ini. Silakan tambahkan transaksi baru.</p>
          </div>
        ) : (
          <TransactionTable
            transactions={transactions}
            isBusy={isBusy}
            onDetail={detailTransactionRoute}
          />
        )}
        {total > 0 && (
          <div className="flex flex-col items-end justify-between gap-4 p-4 md:flex-row">
            <p className="text-sm text-gray-600">
              Showing {(page - 1) * 4 + 1} to {Math.min(page * 4, total)} of {total} entries
            </p>
            <Pagination
              page={page}
              totalPages={totalPages}
              onPageChange={setPage}
              onPrevious={() => setPage((prev) => Math.max(prev - 1, 1))}
              onNext={() => setPage((prev) => Math.min(prev + 1, totalPages))}
            />
          </div>
        )}
      </div>
    </div>
  );
};

export default TransactionData;
