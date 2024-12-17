import React from 'react';

const ProfileImage = ({ imageUrl, altText }) => {
  return (
    <img 
      src={imageUrl || "https://th.bing.com/th/id/OIP.9PPdes_WSxaqUQJxWab16AHaHa?rs=1&pid=ImgDetMain"} 
      className="w-24 h-24 mr-4 rounded-full"
    />
  );
};

export default ProfileImage;
