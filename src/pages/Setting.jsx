import React from 'react';
import { useNavigate } from "react-router-dom";
import ProfileImage from '../components/profile/ProfileImage';
import ProfileInfo from '../components/profile/ProfileInfo';
import NotificationSetting from '../components/setting/NotificationSetting';
import LanguageSetting from '../components/setting/LanguageSetting';
import PageHeader from '../components/PageHeader';
import Skeleton, { SkeletonTheme } from 'react-loading-skeleton';
import 'react-loading-skeleton/dist/skeleton.css';
import useProfileHook from '../hooks/useProfileHook';
import useSettingHook from '../hooks/useSettingHook';

const Setting = () => {
  const { profileData, isBusy } = useProfileHook();
  const navigate = useNavigate();
  const { selectedLanguage, isNotificationEnabled, handleLanguageChange, handleNotificationToggle } = useSettingHook();

  const handleEdit = () => {
    navigate(`/ubah-pengaturan`);
  };

  return (
    <SkeletonTheme baseColor="#d1d1d1" highlightColor="#aaaaaa">
      <div className="flex-1 min-h-screen p-4 py-10 overflow-auto bg-gray-100 lg:ml-64">
        <PageHeader
          title="Pengaturan"
          subtitle="Ini adalah halaman berisi profil admin dan pengaturan website Agrowisata Tepas Papandayan."
          icon={
            <svg className="w-6 h-6 text-gray-600" fill="currentColor" viewBox="0 0 20 20">
              <path fillRule="evenodd" d="M11.49 3.17c-.38-1.56-2.6-1.56-2.98 0a1.532 1.532 0 01-2.286.948c-1.372-.836-2.942.734-2.106 2.106.54.886.061 2.042-.947 2.287-1.561.379-1.561 2.6 0 2.978a1.532 1.532 0 01.947 2.287c-.836 1.372.734 2.942 2.106 2.106a1.532 1.532 0 012.287.947c.379 1.561 2.6 1.561 2.978 0a1.533 1.533 0 012.287-.947c1.372.836 2.942-.734 2.106-2.106a1.533 1.533 0 01.947-2.287c1.561-.379 1.561-2.6 0-2.978a1.532 1.532 0 01-.947-2.287c.836-1.372-.734-2.942-2.106-2.106a1.532 1.532 0 01-2.287-.947zM10 13a3 3 0 100-6 3 3 0 000 6z" clipRule="evenodd" />
            </svg>
          }
        />

        <div className="flex items-center mb-8">
          {
            isBusy() ? (
              <div className="w-32 h-32 overflow-hidden rounded-full">
                <Skeleton circle={true} height="100%" width="100%" />
              </div>
            ) : (<ProfileImage imageUrl={profileData.picture} altText={profileData.username} />
            )
          }
          <div>
            <h2 className="text-xl font-bold text-start">
              {isBusy() ? <Skeleton width="150px" /> : (profileData.username || 'Username')}
            </h2>
            <p className="text-gray-600 text-start">
              {isBusy() ? <Skeleton width="200px" /> : (profileData.bio || '')}
            </p>
            <p className="text-gray-600 text-start">
              {isBusy() ? <Skeleton width="200px" /> : (profileData.address || '')}
            </p>
          </div>
        </div>

        <ProfileInfo profile={profileData} onEditClick={handleEdit} />
        <NotificationSetting initialStatus={isNotificationEnabled} onNotificationToggle={handleNotificationToggle} />
        <LanguageSetting selectedLanguage={selectedLanguage} onLanguageChange={handleLanguageChange} />
      </div>
    </SkeletonTheme>
  );
};

export default Setting;
