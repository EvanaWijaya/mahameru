import React from "react";
import Skeleton, { SkeletonTheme } from "react-loading-skeleton";
import "react-loading-skeleton/dist/skeleton.css";

const VisitorTable = ({ visitors, isBusy }) => {
  return (
    <SkeletonTheme baseColor="#d1d1d1" highlightColor="#aaaaaa">
      <div className="overflow-x-auto">
        <table className="w-full min-w-[800px]">
          <thead>
            <tr className="text-left border-b bg-gray-50">
              <th className="px-4 py-3 text-sm font-semibold">NO</th>
              <th className="px-4 py-3 text-sm font-semibold">NAMA PENGUNJUNG</th>
              <th className="px-4 py-3 text-sm font-semibold">TIPE ID</th>
              <th className="px-4 py-3 text-sm font-semibold">NO ID</th>
              <th className="px-4 py-3 text-sm font-semibold">TIPE TIKET</th>
            </tr>
          </thead>
          <tbody>
            {isBusy ? (
              Array(3)
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
                    <td className="px-4 text-sm pzy-4">
                      <Skeleton width="70%" />
                    </td>
                  </tr>
                ))
            ) : (
              visitors.map((visitor, index) => (
                <tr key={index} className="border-b">
                  <td className="px-4 py-4 text-sm">{index + 1}</td>
                  <td className="px-4 py-4 text-sm">{visitor.visitor_name}</td>
                  <td className="px-4 py-4 text-sm">KTP</td>
                  <td className="px-4 py-4 text-sm">{visitor.visitor_nik}</td>
                  <td className="px-4 py-4 text-sm">{visitor.ticket_name}</td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </SkeletonTheme>
  );
};

export default VisitorTable;
