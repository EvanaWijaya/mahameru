import React, { useEffect, useCallback } from "react";
import DetailItem from "../components/transaction/DetailItem";
import { useParams } from "react-router-dom";
import useTransactionHook from "../hooks/useTransactionHook";

const DetailTransaction = () => {
  const { id } = useParams();
  const {
    detailTransactionData,
    handleGetTransactionById,
  } = useTransactionHook();


  const fetchTransaction = useCallback(() => {
    handleGetTransactionById(id);
  }, [id, handleGetTransactionById]);



  useEffect(() => {
    fetchTransaction();
  }, [fetchTransaction]);

function formatDate(dateString) {
  const months = [
    "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December",
  ];

  const date = new Date(dateString);

  const day = date.getDate();
  const monthName = months[date.getMonth()];
  const year = date.getFullYear();

  let hours = date.getHours();
  let minutes = date.getMinutes();

  date.setHours(date.getHours() + 7);
  hours = date.getHours();
  minutes = date.getMinutes();

  hours = hours < 10 ? `0${hours}` : hours;
  minutes = minutes < 10 ? `0${minutes}` : minutes;

  return `${day} ${monthName} ${year} - ${hours}.${minutes} WIB`;
}



  const transactionDetails = [
    { label: "No Transaksi", value: detailTransactionData.transaction_number },
    { label: "Nama", value: detailTransactionData.username },
    { label: "Kategori", value: detailTransactionData.tickets[0] },
    { label: "Tanggal Transaksi", value: formatDate(detailTransactionData.date) },
    { label: "Metode Pembayaran", value: detailTransactionData.payment_method },
    { label: "Total Bayar", value: detailTransactionData.total_price },
    { label: "Status", value: detailTransactionData.status, isStatus: true },
  ];

  return (
    <div className="flex-1 p-4 bg-gray-100 md:p-6 md:pl-64">
      <div className="px-3 mb-6">
        <h1 className="mb-2 text-xl font-semibold text-transparent md:text-2xl text-start">Detail Transaksi</h1>
        <h1 className="mb-2 text-xl font-semibold md:text-2xl text-start">Detail Transaksi</h1>
        <p className="mb-6 text-sm text-gray-600 md:text-base text-start">
          Ini adalah halaman untuk melihat detail transaksi pengunjung Agrowisata Tepas Papandayan yang masuk.
        </p>
        <div className="p-4 bg-white rounded-lg shadow md:p-6">
          <div className="space-y-4">
            {transactionDetails.map((item, index) => (
              <DetailItem
                key={index}
                label={item.label}
                value={item.value}
                isStatus={item.isStatus || false}
              />
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default DetailTransaction;
