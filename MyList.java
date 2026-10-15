import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.*;
import java.awt.*;

public class MyList extends JFrame {

    Container cp;
    JPanel panelbody, panelhead, wrappanel;
    JLabel weightlabel, weight;
    String user;
    SelectionMenu menu;

    public MyList(SelectionMenu menu, String user) {
        this.menu = menu;
        this.user = user;
        Intitial();
        setComponent();
        setupFrame();
    }

    public void Intitial() {
        cp = this.getContentPane();
        cp.setLayout(new BorderLayout());
        panelhead = new JPanel();
        panelhead.setLayout(new GridBagLayout());
        panelhead.setPreferredSize(new Dimension(1000, 200));

        wrappanel = new JPanel();
        wrappanel.setLayout(new GridBagLayout());

        panelbody = new JPanel();
        panelbody.setLayout(new GridBagLayout());
        panelbody.setBackground(Color.LIGHT_GRAY);

    }

    public void setComponent() {

        GridBagConstraints ghead = new GridBagConstraints();
        JLabel mylist = new JLabel("My List");
        mylist.setFont(new Font("", Font.BOLD, 40));
        ghead.gridx = 0;
        ghead.gridy = 0;
        ghead.weightx = 1;
        ghead.anchor = GridBagConstraints.CENTER;
        panelhead.add(mylist, ghead);

        JLabel user = new JLabel(getusername());
        ghead.gridx = 0;
        ghead.gridy = 1;
        ghead.anchor = GridBagConstraints.WEST;
        user.setFont(new Font("", Font.BOLD, 30));
        ghead.insets = new Insets(15, 100, 0, 0);
        panelhead.add(user, ghead);

        JLabel name = new JLabel(getname(getusername()));
        ghead.gridx = 0;
        ghead.gridy = 2;
        ghead.anchor = GridBagConstraints.WEST;
        name.setFont(new Font("", Font.BOLD, 30));
        ghead.insets = new Insets(10, 100, 0, 0);
        panelhead.add(name, ghead);

        JLabel year = new JLabel("Year :" + getyear(getusername()));
        ghead.gridx = 0;
        ghead.gridy = 3;
        ghead.anchor = GridBagConstraints.WEST;
        year.setFont(new Font("", Font.BOLD, 25));
        ghead.insets = new Insets(10, 100, 0, 0);
        panelhead.add(year, ghead);

        GridBagConstraints gwrap = new GridBagConstraints();
        gwrap.insets = new Insets(0, 0, 20, 0);
        JScrollPane scrollPane = new JScrollPane(panelbody);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        gwrap.gridx = 0;
        gwrap.gridy = 0;
        wrappanel.add(scrollPane, gwrap);

        weightlabel = new JLabel("Total Weight : " + calweight(getusername()));
        gwrap.gridx = 0;
        gwrap.gridy = 1;
        gwrap.anchor = GridBagConstraints.EAST;
        gwrap.insets = new Insets(0, 0, 60, 20);
        weightlabel.setFont(new Font("", Font.BOLD, 15));
        wrappanel.add(weightlabel, gwrap);

        writesubject(this.user);

        cp.add(panelhead, BorderLayout.NORTH);
        cp.add(wrappanel, BorderLayout.CENTER);
    }

    public void setupFrame() {
        this.setSize(1000, 800);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                menu.enableAddButton();
            }
        });
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public String getusername() {
        return user;
    }

    public String getname(String user) {
        try (BufferedReader br = new BufferedReader(new FileReader("Lib/Nisit.csv"))) {
            br.readLine();
            String s;
            while ((s = br.readLine()) != null) {
                String data[] = s.split(",");

                if (data[0].equals(user)) {
                    return data[1];
                }

            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return "";
    }

    public String getyear(String user) {
        try (BufferedReader br = new BufferedReader(new FileReader("Lib/sub_regis.csv"))) {
            br.readLine();
            String s;
            while ((s = br.readLine()) != null) {
                String data[] = s.split(",");

                if (data[0].equals(user)) {
                    return data[4];
                }

            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return "";
    }

    public void writesubject(String user) {
        GridBagConstraints glabel = new GridBagConstraints();
        glabel.anchor = GridBagConstraints.WEST;

        int row = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("Lib/sub_regis.csv"))) {
            String s;
            while ((s = br.readLine()) != null) {
                String data[] = s.split(",");

                if (data[0].equals(user)) {
                    String code = data[1];
                    String subject = data[2];
                    String weight = data[3];

                    glabel.gridx = 0;
                    glabel.gridy = row;
                    glabel.insets = new Insets(10, 10, 10, 50);
                    JLabel labelCode = new JLabel(code);
                    labelCode.setFont(new Font("", Font.PLAIN, 20));
                    panelbody.add(labelCode, glabel);

                    glabel.gridx = 1;
                    glabel.insets = new Insets(10, 100, 10, 50);
                    JLabel labelsubject = new JLabel(subject);
                    labelsubject.setFont(new Font("", Font.PLAIN, 20));
                    panelbody.add(labelsubject, glabel);

                    glabel.gridx = 2;
                    glabel.insets = new Insets(10, 100, 10, 10);
                    JLabel labelweight = new JLabel(weight);
                    labelweight.setFont(new Font("", Font.PLAIN, 20));
                    panelbody.add(labelweight, glabel);
                    row++;
                }
            }
            glabel.gridx = 0;
            glabel.gridy = row;
            glabel.weighty = 1.0;
            panelbody.add(new JLabel(""), glabel);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        
        panelbody.revalidate();
        panelbody.repaint();

    }

    public int calweight(String user) {
        int total = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("Lib/sub_regis.csv"))){
            String s;
            int data_weight;
            while ((s = br.readLine()) != null) {
                String data[] = s.split(",");
                if (data[0].equals(user)) {
                    data_weight = Integer.parseInt(data[3]);
                    total = total + data_weight;
                }
            }
        } catch (Exception ew) {
            ew.printStackTrace();
        }
        return total;
    }

}