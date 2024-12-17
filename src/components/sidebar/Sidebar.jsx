import React, { useState } from "react";
import Header from "./Header";
import LogoSection from "./LogoSection";
import SidebarMenu from "./SidebarMenu";
import useProfileHooks from "../../hooks/useProfileHook";
const Sidebar = () => {
  const { profileData, isBusy } = useProfileHooks();
  const [isOpen, setIsOpen] = useState(false);

  const toggleSidebar = () => setIsOpen(!isOpen);

  return (
    <>
      <Header profileData={profileData} isOpen={isOpen} toggleSidebar={toggleSidebar} isBusy={isBusy} />
      <div
        className={`fixed top-0 left-0 w-64 bg-white h-full border-r transform ${
          isOpen ? "translate-x-0" : "-translate-x-full"
        } transition-transform duration-300 md:translate-x-0 z-40`}
      >
        <LogoSection />
        <SidebarMenu />
      </div>
    </>
  );
};

export default Sidebar;
