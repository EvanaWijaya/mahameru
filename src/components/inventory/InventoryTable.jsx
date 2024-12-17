import React from "react";
import Skeleton, { SkeletonTheme } from "react-loading-skeleton";
import "react-loading-skeleton/dist/skeleton.css";
import ActionButton from "../ActionButton";
import { formatPrice } from "../../utils/formatPrice";

const InventoryTable = ({ inventorys, onDelete, onEdit, isBusy }) => {
  return (
    <SkeletonTheme baseColor="#d1d1d1" highlightColor="#aaaaaa">
      <div className="overflow-x-auto">
        <table className="w-full min-w-[800px]">
          <thead>
            <tr className="text-left border-b bg-gray-50">
              <th className="px-4 py-3 text-sm font-semibold">GAMBAR</th>
              <th className="px-4 py-3 text-sm font-semibold">KODE</th>
              <th className="px-4 py-3 text-sm font-semibold">NAMA BARANG</th>
              <th className="px-4 py-3 text-sm font-semibold">JUMLAH</th>
              <th className="px-4 py-3 text-sm font-semibold">HARGA</th>
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
              : inventorys.map((inventory) => (
                  <tr key={inventory.id} className="border-b">
                    <td className="px-4 py-4 text-sm">
                      <img
                        src={inventory.picture}
                        alt={inventory.name}
                        className="object-cover w-12 h-12"
                      />
                    </td>
                    <td className="px-4 py-4 text-sm">{inventory.code}</td>
                    <td className="px-4 py-4 text-sm">{inventory.name}</td>
                    <td className="px-4 py-4 text-sm">{inventory.stock_quantity}</td>
                    <td className="px-4 py-4 text-sm">{formatPrice(inventory.price)}</td>
                    <td className="px-4 py-4">
                      <div className="flex justify-start gap-2">
                        <ActionButton
                          type="edit"
                          onClick={() => onEdit(inventory.id)}
                        />
                        <ActionButton
                          type="delete"
                          onClick={() => onDelete(inventory.id)}
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

export default InventoryTable;
