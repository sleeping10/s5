package sample;

import com.convertapi.client.Config;
import com.convertapi.client.ConvertApi;
import com.convertapi.client.Param;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class CreatePdf {

    private ArrayList<String> service = new ArrayList<>();
    private ArrayList<String> price = new ArrayList<>();
    // Putting in all the information in the Docx file.
    private String osType = "";
    public void createDocument(Booking booking) throws Exception {
        DecimalFormat decimalFormat = new DecimalFormat(".##");
        try {
            if (System.getProperty("os.name").equalsIgnoreCase("linux")){
                osType ="s5/";
            }
            for (int i = 0; i < booking.getServices().size(); i++) {
                service.add(booking.getServices().get(i).getServiceName());
                price.add(booking.getServices().get(i).getCostAndDiscountAsString());

            }


            Date myDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timeStamp = sdf.format(myDate);
            XWPFDocument doc = new XWPFDocument(OPCPackage.open(osType +"src//FXML/test.docx"));
            for (XWPFParagraph p : doc.getParagraphs()) {
                List<XWPFRun> runs = p.getRuns();
                if (runs != null) {
                    for (XWPFRun r : runs) {
                        String text = r.getText(0);
                        if (text != null && text.contains("date")) {
                            text = text.replace("date", timeStamp);
                            r.setText(text, 0);
                        }

                    }
                }
            }

            for (XWPFParagraph p : doc.getParagraphs()) {
                List<XWPFRun> runs = p.getRuns();
                if (runs != null) {
                    for (XWPFRun r : runs) {
                        String text = r.getText(0);
                        if (text != null && text.contains("name")) {
                            text = text.replace("name", DBC.getInstance().getCurrentAcc().getName());
                            r.setText(text, 0);
                        }

                    }
                }
            }

            for (XWPFParagraph p : doc.getParagraphs()) {
                List<XWPFRun> runs = p.getRuns();
                if (runs != null) {
                    for (XWPFRun r : runs) {
                        String text = r.getText(0);
                        if (text != null && text.contains("email")) {
                            text = text.replace("email", DBC.getInstance().getCurrentAcc().getEmail());
                            r.setText(text, 0);
                        }

                    }
                }
            }

            for (XWPFParagraph p : doc.getParagraphs()) {
                List<XWPFRun> runs = p.getRuns();
                if (runs != null) {
                    for (XWPFRun r : runs) {
                        String text = r.getText(0);
                        if (text != null && text.contains("nbr")) {
                            text = text.replace("nbr", DBC.getInstance().getCurrentAcc().getPhoneNr());
                            r.setText(text, 0);
                        }

                    }
                }
            }

            System.out.println("allt är klart ");
            // Andra delen
            double totalP = 0;
            for (XWPFParagraph p : doc.getParagraphs()) {
                java.util.List<XWPFRun> runs = p.getRuns();
                if (runs != null) {
                    for (XWPFRun r : runs) {
                        String text = r.getText(0);
                        if (text != null && text.contains("Items:")) {
                            r.addTab();
                            r.setText(text, 0);
                            for (int a = 0; a < service.size(); a++) {
                                r.addBreak();        //new line
                                r.setText(checkSpacing(service.get(a)));
                                r.setText(price.get(a));
                                totalP += booking.getServices().get(a).getCurrentCost();
                            }
                        }
                    }
                }
            }

            for (XWPFParagraph p : doc.getParagraphs()) {
                List<XWPFRun> runs = p.getRuns();
                if (runs != null) {
                    for (XWPFRun r : runs) {
                        String text = r.getText(0);
                        if (text != null && text.contains("price")) {
                            text = text.replace("price", String.valueOf(decimalFormat.format(totalP) + "$"));
                            r.setText(text, 0);
                        }

                    }
                }
            }
            for (XWPFParagraph p : doc.getParagraphs()) {
                List<XWPFRun> runs = p.getRuns();
                if (runs != null) {
                    for (XWPFRun r : runs) {
                        String text = r.getText(0);
                        if (text != null && text.contains("date1")) {
                            text = text.replace("date1", booking.getDate().toString());
                            System.out.println(booking.getDate().toString());
                            r.setText(text, 0);
                        }

                    }
                }
            }

            for (XWPFParagraph p : doc.getParagraphs()) {
                List<XWPFRun> runs = p.getRuns();
                if (runs != null) {
                    for (XWPFRun r : runs) {
                        String text = r.getText(0);
                        if (text != null && text.contains("ida")) {
                            text = text.replace("ida", String.valueOf(booking.getBookingID()));
                            r.setText(text, 0);

                        }

                    }
                }
            }

            for (XWPFParagraph p : doc.getParagraphs()) {
                List<XWPFRun> runs = p.getRuns();
                if (runs != null) {
                    for (XWPFRun r : runs) {
                        String text = r.getText(0);
                        if (text != null && text.contains("plate")) {
                            text = text.replace("plate", booking.getLicensePlate());
                            r.setText(text, 0);
                        }

                    }
                }
            }

            doc.write(new FileOutputStream(osType +"src/FXML/test1.docx"));

            //api to covert from Docx to pdf.
            Config.setDefaultSecret("GTV4WT1bbidv7rUV");
            ConvertApi.convert("docx", "pdf",
                    new Param("File", Paths.get(osType +"src/FXML/test1.docx"))
            ).get().saveFilesSync(Paths.get(osType +"src/FXML"));

            String homeDir = System.getProperty("user.home");
            Files.move(Path.of(osType +"src/FXML/test1.pdf") , Path.of(homeDir+"/booking confirmation.pdf"),REPLACE_EXISTING);
            Files.deleteIfExists(Path.of(osType +"src/FXML/test.docx"));
            Files.deleteIfExists(Path.of(osType +"src/FXML/test1.docx"));
            System.out.println("done");



    } catch (Exception e) {
            e.printStackTrace();
        }


    }

        //Fixing the spacing for the document.
    public String checkSpacing(String check) {
        String tabs = "";
        if (check.equals("inspection_basic")) {
            tabs = "\t\t\t";
            return "Basic Inspection" + tabs;
        } else if (check.equals("inspection_advanced")) {
            tabs = "\t\t\t";
            return "Advanced Inspection" + tabs;

        } else if (check.equals("service_oilchange")) {
            tabs = "\t\t\t\t";
            return "Oil Change" + tabs;
        } else if (check.equals("service_ac")) {
            tabs = "\t\t\t\t";
            return "Ac Service" + tabs;

        } else if (check.equals("service_wheelchange")) {
            tabs = "\t\t\t";
            return "Wheel Change" + tabs;

        } else if (check.equals("service_timingbelt")) {
            tabs = "\t\t\t";
            return "Timing Belt Change" + tabs;

        } else if (check.equals("service_battery")) {
            tabs = "\t\t\t";
            return "Change Battery" + tabs;

        } else if (check.equals("service_wheelalignment")) {
            tabs = "\t\t\t";
            return "Wheel Alignment" + tabs;

        } else if (check.equals("wash_basic_exterior")) {
            tabs = "\t\t\t";
            return "Basic Exterior Wash" + tabs;

        } else if (check.equals("wash_premium_exterior")) {
            tabs = "\t\t";
            return "Premium Exterior Wash" + tabs;

        } else if (check.equals("wash_basic_interior")) {
            tabs = "\t\t\t\t";
            return "Interior Wash" + tabs;

        } else if (check.equals("wash_premium_interior")) {
            tabs = "\t\t";
            return "Interior Wash Premium" + tabs;

        } else if (check.equals("wash_compl_basic")) {
            tabs = "\t\t\t";
            return "Complete Wash" + tabs;
        } else if (check.equals("wash_compl_premium")) {
            tabs = "\t\t";
            return "Complete Wash Premium" + tabs;

        } else return null;


    }


}


