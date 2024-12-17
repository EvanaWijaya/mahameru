import React, { useState } from "react";
import PageHeader from "../components/PageHeader";
import Pagination from "../components/Pagination";
import VisitorTable from "../components/visitor/VisitorTable";
import SearchBar from "../components/SearchBar";
import useVisitorHook from "../hooks/useVisitorHook";

const VisitorData = () => {
  const {
    visitors,
    page,
    setPage,
    total,
    totalPages,
    setQuery,
    isBusy,
    setFilterQuery,
    handleFilterVisitors,
  } = useVisitorHook();

  const [isFilterOpen, setIsFilterOpen] = useState(false);
  const [activeSort, setActiveSort] = useState(null);

  const handleFilterVisitorsSelect = (sortOrder) => {
    setFilterQuery(sortOrder);
    setActiveSort(sortOrder);
    setIsFilterOpen(false);
    handleFilterVisitors(sortOrder);
  };

  return (
    <div className="flex-1 min-h-screen p-4 py-10 bg-gray-100 lg:ml-64">
      <PageHeader
        title="Data Pengunjung"
        subtitle="Ini adalah halaman untuk melihat data pengunjung Agrowisata Tepas Papandayan."
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
              d="M15 19.128a9.38 9.38 0 0 0 2.625.372 9.337 9.337 0 0 0 4.121-.952 4.125 4.125 0 0 0-7.533-2.493M15 19.128v-.003c0-1.113-.285-2.16-.786-3.07M15 19.128v.106A12.318 12.318 0 0 1 8.624 21c-2.331 0-4.512-.645-6.374-1.766l-.001-.109a6.375 6.375 0 0 1 11.964-3.07M12 6.375a3.375 3.375 0 1 1-6.75 0 3.375 3.375 0 0 1 6.75 0Zm8.25 2.25a2.625 2.625 0 1 1-5.25 0 2.625 2.625 0 0 1 5.25 0Z"
            />
          </svg>
        }
      />
      <div className="overflow-hidden bg-white rounded-lg shadow-sm">
        <div className="p-4 border-b">
          <div className="flex flex-col gap-4 md:flex-row">
            <button
              onClick={() => setIsFilterOpen((prev) => !prev)}
              className="flex items-center justify-center w-full gap-2 px-4 py-2 text-gray-600 rounded-lg bg-gray-50 md:w-auto md:justify-start"
            >
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
                  d="M3 4a1 1 0 011-1h16a1 1 0 011 1v2.586a1 1 0 01-.293.707l-6.414 6.414a1 1 0 00-.293.707V17l-4 4v-6.586a1 1 0 00-.293-.707L3.293 7.293A1 1 0 013 6.586V4z"
                />
              </svg>
              Filter
            </button>
            {isFilterOpen && (
              <div className="absolute z-10 w-48 p-2 mt-2 bg-white border rounded shadow-lg">
                <button
                  className={`block w-full px-4 py-2 text-sm text-left text-gray-700 hover:bg-gray-100 ${activeSort === "ASC" ? "bg-[#00796B] text-white" : ""}`}
                  onClick={() => handleFilterVisitorsSelect("ASC")}
                >
                  A-Z
                </button>
                <button
                  className={`block w-full px-4 py-2 text-sm text-left text-gray-700 hover:bg-gray-100 ${activeSort === "DESC" ? "bg-[#00796B] text-white" : ""}`}
                  onClick={() => handleFilterVisitorsSelect("DESC")}
                >
                  Z-A
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
            <p>Tidak ada data pengunjung saat ini. Silakan tambahkan pengunjung baru.</p>
          </div>
        ) : (
          <VisitorTable visitors={visitors} isBusy={false} />
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

export default VisitorData;
