package com.juaracoding.websitearticle.utils;

import com.juaracoding.websitearticle.configuration.OtherConfig;
import com.juaracoding.websitearticle.core.SMTPCore;
import com.juaracoding.websitearticle.handler.ResourceNotFoundException;

public class ExecuteSMTP {

    StringBuilder stringBuilder = new StringBuilder();
    private String [] strException = new String[2];

    public ExecuteSMTP() {
        strException[0] = "ExecuteSMTP";
    }

    public Boolean sendSMTPToken(String mailAddress, String subject, String [] strVerification,String pathFile)
    {
        try
        {
            if(OtherConfig.getFlagSMTPActive().equalsIgnoreCase("y") && !mailAddress.equals(""))
            {
                String strContent = new ReadTextFileSB(pathFile).getContentFile();
                strContent = strContent.replace("#JKVM3NH",strVerification[0]);//Kepentingan
                strContent = strContent.replace("XF#31NN",strVerification[1]);//Nama Lengkap
                strContent = strContent.replace("8U0_1GH$",strVerification[2]);//TOKEN

                String [] strEmail = {mailAddress};
                SMTPCore sc = new SMTPCore();
                return  sc.sendMailWithAttachment(strEmail,
                        subject,
                        strContent,
                        "SSL",null);
            }
        }
        catch (Exception e)
        {
            strException[1]="sendToken(String mailAddress, String subject, String purpose, String token) -- LINE 38";
            LoggingFile.exceptionStringz(strException,e,OtherConfig.getFlagLogging());
            return false;
        }
        return true;
    }
}