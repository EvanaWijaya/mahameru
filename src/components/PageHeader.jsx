import React from "react";

const PageHeader = ({ title, subtitle, icon }) => {
  return (
    <div className="mb-6">
      <h2 className="text-2xl font-semibold text-transparent">{title}</h2>
      <div className={`flex items-center gap-4 ${icon ? "mb-4" : "mb-2"}`}>
        {icon && icon}
        <h2 className={`text-2xl ${icon ? "font-semibold" : "font-bold"} text-start`}>
          {title}
        </h2>
      </div>
      <p className={`text-sm ${icon ? "mb-6 md:text-base" : ""} text-gray-600 text-start`}>
        {subtitle}
      </p>
    </div>
  );
};

export default PageHeader;
