import React, { useEffect } from 'react';
import { Line } from 'react-chartjs-2';
import { chartOptions } from './ChartConfig';
import PageHeader from "../PageHeader";
import useTransactionHook from "../../hooks/useTransactionHook";
import { formatPrice } from "../../utils/formatPrice";
const DashboardContent = () => {
    const {
        totalTransaction,
        totalTransactionTicket,
        totalTransactionInventory,
        totalTransactionSuccess,
        chartData,
        handleGetTransactionSummary,
        transactionSummary,
      } = useTransactionHook();
      useEffect(() => {
        handleGetTransactionSummary();
      }, []);
    return (
        <div className="flex-1 min-h-screen p-4 py-10 overflow-y-auto bg-gray-100 lg:ml-64">

            <PageHeader
                title="Dashboard"
                subtitle="  Ini adalah halaman untuk menampilkan informasi secara visual di Agrowisata Tepas Papandayan."
                icon={
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-5 h-5">
                        <path strokeLinecap="round" strokeLinejoin="round" d="M3.75 6A2.25 2.25 0 0 1 6 3.75h2.25A2.25 2.25 0 0 1 10.5 6v2.25a2.25 2.25 0 0 1-2.25 2.25H6a2.25 2.25 0 0 1-2.25-2.25V6ZM3.75 15.75A2.25 2.25 0 0 1 6 13.5h2.25a2.25 2.25 0 0 1 2.25 2.25V18a2.25 2.25 0 0 1-2.25 2.25H6A2.25 2.25 0 0 1 3.75 18v-2.25ZM13.5 6a2.25 2.25 0 0 1 2.25-2.25H18A2.25 2.25 0 0 1 20.25 6v2.25A2.25 2.25 0 0 1 18 10.5h-2.25a2.25 2.25 0 0 1-2.25-2.25V6ZM13.5 15.75a2.25 2.25 0 0 1 2.25-2.25H18a2.25 2.25 0 0 1 20.25V18A2.25 2.25 0 0 1 18h-2.25A2.25 2.25 0 0 1 13.5V18Z" />
                    </svg>
                }
            />

            <div className="grid grid-cols-1 gap-4 md:grid-cols-4">
                <div className="p-4 text-center bg-green-100 rounded-lg shadow-md">
                    <h2 className="text-lg font-semibold text-gray-800">Jumlah Transaksi</h2>
                    <p className="text-xl font-bold text-gray-900">{totalTransactionSuccess.transaction_total}</p>
                </div>
                <div className="p-4 text-center bg-red-100 rounded-lg shadow-md">
                    <h2 className="text-lg font-semibold text-gray-800">Jumlah Pemasukan Tiket</h2>
                    <p className="text-xl font-bold text-gray-900">{formatPrice(totalTransactionTicket.total_price_tickets)}</p>
                </div>
                <div className="p-4 text-center bg-blue-100 rounded-lg shadow-md">
                    <h2 className="text-lg font-semibold text-gray-800">Jumlah Pemasukan Inventaris</h2>
                    <p className="text-xl font-bold text-gray-900">{formatPrice(totalTransactionInventory.total_price_inventories)}</p>
                </div>
                <div className="p-4 text-center bg-yellow-100 rounded-lg shadow-md">
                    <h2 className="text-lg font-semibold text-gray-800">Total Pemasukan</h2>
                    <p className="text-xl font-bold text-gray-900">{formatPrice(totalTransaction.total_price_amount)}</p>
                </div>
            </div>

            <div className="grid grid-cols-1 gap-6 mt-8 md:grid-cols-2">
        <div className="p-4 bg-white rounded-lg shadow-md">
          <h2 className="mb-4 text-lg font-semibold">Grafik Pemasukan</h2>
          <Line data={chartData.financialData} options={chartOptions} />
        </div>
        <div className="p-4 bg-white rounded-lg shadow-md">
          <h2 className="mb-4 text-lg font-semibold">Grafik Penjualan Tiket</h2>
          <Line data={chartData.ticketData} options={chartOptions} />
        </div>
      </div>
        </div>
    );
};

export default DashboardContent;
