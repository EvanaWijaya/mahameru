import React from "react";
import { Link } from "react-router-dom";
import Skeleton from "react-loading-skeleton";
import 'react-loading-skeleton/dist/skeleton.css';

const Header = ({ profileData, isOpen, toggleSidebar, isBusy }) => (
  <div className="fixed top-0 left-0 w-full bg-white z-5">
    <div className="flex items-center justify-between px-4 h-14">
      <div className="flex items-center ml-0 md:ml-64">
        <span className="text-lg font-bold">
          {isBusy() ? <Skeleton width={100} /> : `Halo, ${profileData.username || "User"}!`}
        </span>
        <span className="ml-2 text-sm text-gray-500">
          {isBusy() ? <Skeleton width={150} /> : "Selamat Datang Di Verdant"}
        </span>
      </div>
      <div className="flex items-center">
        <Link to="/pengaturan" className="flex items-center cursor-pointer">
          {isBusy() ? (
            <Skeleton circle={true} height={32} width={32} />
          ) : (
            <img
              src={profileData.picture || "https://th.bing.com/th/id/OIP.9PPdes_WSxaqUQJxWab16AHaHa?rs=1&pid=ImgDetMain"}
              alt="Profile"
              className="w-8 h-8 rounded-full"
            />
          )}
          <span className="px-5">
            {isBusy() ? <Skeleton width={100} /> : profileData.username || "Username"}
          </span>
        </Link>
        <button className="p-2 md:hidden focus:outline-none" onClick={toggleSidebar}>
          <svg
            className="w-6 h-6"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              d={isOpen ? "M6 18L18 6M6 6l12 12" : "M4 6h16M4 12h16M4 18h16"}
            ></path>
          </svg>
        </button>
      </div>
    </div>
  </div>
);

export default Header;
