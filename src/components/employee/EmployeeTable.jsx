import React from "react";
import Skeleton, { SkeletonTheme } from "react-loading-skeleton";
import "react-loading-skeleton/dist/skeleton.css";
import ActionButton from "../ActionButton";

const EmployeeTable = ({ employees, onDelete, onEdit, isBusy }) => {
  return (
    <SkeletonTheme baseColor="#d1d1d1" highlightColor="#aaaaaa">
      <div className="overflow-x-auto">
        <table className="w-full min-w-[800px]">
          <thead>
            <tr className="text-left border-b bg-gray-50">
              <th className="px-4 py-3 text-sm font-semibold">FOTO</th>
              <th className="px-4 py-3 text-sm font-semibold">NIP</th>
              <th className="px-4 py-3 text-sm font-semibold">NAMA</th>
              <th className="px-4 py-3 text-sm font-semibold">POSISI</th>
              <th className="px-4 py-3 text-sm font-semibold">NO HP</th>
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
              : employees.map((pegawai) => (
                  <tr key={pegawai.id} className="border-b">
                    <td className="px-4 py-4 text-sm">
                      <img
                        src={pegawai.picture}
                        alt={pegawai.name}
                        className="object-cover w-12 h-12"
                      />
                    </td>
                    <td className="px-4 py-4 text-sm">{pegawai.nip}</td>
                    <td className="px-4 py-4 text-sm">{pegawai.name}</td>
                    <td className="px-4 py-4 text-sm">{pegawai.position}</td>
                    <td className="px-4 py-4 text-sm">{pegawai.phone_number}</td>
                    <td className="px-4 py-4">
                      <div className="flex justify-start gap-2">
                        <ActionButton
                          type="edit"
                          onClick={() => onEdit(pegawai.id)}
                        />
                        <ActionButton
                          type="delete"
                          onClick={() => onDelete(pegawai.id)}
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

export default EmployeeTable;
