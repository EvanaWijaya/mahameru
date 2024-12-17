import { useState } from 'react';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css'; 

const useSettingHook = () => {
  const [selectedLanguage, setSelectedLanguage] = useState("Bahasa Indonesia");
  const [isNotificationEnabled, setIsNotificationEnabled] = useState(false);

  const handleLanguageChange = (language) => {
    if (language !== "Bahasa Indonesia") {
      toast.error("Fitur ini belum tersedia."); 
      return;
    }
    setSelectedLanguage(language);
  };

  const handleNotificationToggle = (status) => {
    toast.error("Fitur ini belum tersedia."); 
    setIsNotificationEnabled(false);
  };

  return {
    selectedLanguage,
    isNotificationEnabled,
    handleLanguageChange,
    handleNotificationToggle
  };
};

export default useSettingHook;
