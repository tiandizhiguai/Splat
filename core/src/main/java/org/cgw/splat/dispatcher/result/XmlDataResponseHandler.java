package org.cgw.splat.dispatcher.result;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class XmlDataResponseHandler implements DataResponseHandler {

    @Override
    public String handleData(Object context) {
        JAXBContext jc = null;
        try {
            jc = JAXBContext.newInstance(context.getClass());
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Marshaller m = null;
        try {
            m = jc.createMarshaller();
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        OutputStream os = null;
        try {
            os = new FileOutputStream("d:/foo.xml");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            m.marshal(context, os);
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


}
