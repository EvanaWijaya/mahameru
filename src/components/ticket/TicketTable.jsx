import React from "react";
import Skeleton, { SkeletonTheme } from "react-loading-skeleton";
import "react-loading-skeleton/dist/skeleton.css";
import ActionButton from "../ActionButton";
import { formatPrice } from "../../utils/formatPrice";

const TicketTable = ({ tickets, onDelete, onEdit, isBusy }) => {
  return (
    <SkeletonTheme baseColor="#d1d1d1" highlightColor="#aaaaaa">
      <div className="overflow-x-auto">
        <table className="w-full min-w-[800px]">
          <thead>
            <tr className="text-left border-b bg-gray-50">
              <th className="px-4 py-3 text-sm font-semibold">JENIS TIKET</th>
              <th className="px-4 py-3 text-sm font-semibold">HARGA</th>
              <th className="px-4 py-3 text-sm font-semibold">STOK</th>
              <th className="px-4 py-3 text-sm font-semibold">BATAS PENGUNJUNG</th>
              <th className="px-4 py-3 text-sm font-semibold">DETAIL</th>
              <th className="px-4 py-3 text-sm font-semibold">AKSI</th>
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
              : tickets.map((ticket) => (
                  <tr key={ticket.id} className="border-b">
                    <td className="px-4 py-4 text-sm">{ticket.name}</td>
                    <td className="px-4 py-4 text-sm">{formatPrice(ticket.price)}</td>
                    <td className="px-4 py-4 text-sm">{ticket.stock_quantity}</td>
                    <td className="px-4 py-4 text-sm">{ticket.visitor_quantity}</td>
                    <td className="px-4 py-4 text-sm">{ticket.description}</td>
                    <td className="px-4 py-4">
                      <div className="flex justify-start gap-2">
                        <ActionButton
                          type="edit"
                          onClick={() => onEdit(ticket.id)}
                        />
                        <ActionButton
                          type="delete"
                          onClick={() => onDelete(ticket.id)}
                        />
                      </div>
                    </td>
                  </tr>
                ))}
          </tbody>
        </table>
      </div>
    </SkeletonTheme>
  );
};

export default TicketTable;
