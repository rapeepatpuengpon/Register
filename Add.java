import java.awt.event.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;

public class Add extends JFrame implements ActionListener {

    Container cp;
    JPanel headPanel, bodyPanel, listPanel;
    JLabel regis, user, name , year, weight;
    JLabel labelCode, labelSubject, currentweight;
    JLabel head, warning;
    JRadioButton radio;
    ButtonGroup group;
    JScrollPane scrollPane;
    JComboBox<String> comboBox;
    JButton add;
    SelectionMenu menu;
    String currentuser;

    public Add(SelectionMenu menu, String user) {
        this.menu = menu;
        this.currentuser = user;
        Intitial();
        setComponent();
        setupFrame();
    }

    public void Intitial() {
        cp = this.getContentPane();
        cp.setLayout(new BorderLayout());
        cp.setBackground(Color.WHITE);

        headPanel = new JPanel();
        headPanel.setLayout(new GridBagLayout());
        headPanel.setPreferredSize(new Dimension(1000, 200));
        headPanel.setBackground(Color.white);

        bodyPanel = new JPanel(new GridBagLayout());
        bodyPanel.setBackground(Color.white);

        listPanel = new JPanel(new GridBagLayout());
        listPanel.setBackground(Color.WHITE);
    }

    public void setComponent() {
        GridBagConstraints ghead = new GridBagConstraints();
        regis = new JLabel("Registration");
        regis.setFont(new Font("", Font.BOLD, 40));
        ghead.gridx = 0;
        ghead.gridy = 0;
        ghead.weightx = 1;
        ghead.anchor = GridBagConstraints.CENTER;
        headPanel.add(regis, ghead);

        user = new JLabel(getusername());
        ghead.gridx = 0;
        ghead.gridy = 1;
        ghead.anchor = GridBagConstraints.WEST;
        user.setFont(new Font("", Font.BOLD, 25));
        ghead.insets = new Insets(15, 100, 0, 0);
        headPanel.add(user, ghead);

        name = new JLabel(getname(getusername()));
        ghead.gridx = 0;
        ghead.gridy = 2;
        ghead.anchor = GridBagConstraints.WEST;
        name.setFont(new Font("", Font.BOLD, 25));
        ghead.insets = new Insets(10, 100, 0, 0);
        headPanel.add(name, ghead);

        year = new JLabel("Year :" + getyear(currentuser));
        ghead.gridx = 0;
        ghead.gridy = 3;
        ghead.anchor = GridBagConstraints.WEST;
        year.setFont(new Font("", Font.BOLD, 25));
        ghead.insets = new Insets(10, 100, 0, 0);
        headPanel.add(year, ghead);

        GridBagConstraints gbody = new GridBagConstraints();
        scrollPane = new JScrollPane(listPanel);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        gbody.anchor = GridBagConstraints.WEST;
        gbody.gridx = 0;
        gbody.gridy = 0;
        bodyPanel.add(scrollPane, gbody);

        add = new JButton("Add");
        gbody.gridx = 0;
        gbody.gridy = 1;
        gbody.anchor = GridBagConstraints.EAST;
        gbody.insets = new Insets(15, 0, 100, 0);
        bodyPanel.add(add, gbody);

        warning = new JLabel();
        gbody.gridx = 0;
        gbody.gridy = 1;
        gbody.anchor = GridBagConstraints.WEST;
        gbody.insets = new Insets(10, 0, 100, 0);
        warning.setFont(new Font("", Font.PLAIN, 20));
        bodyPanel.add(warning, gbody);

        add.addActionListener(this);

        cp.add(bodyPanel, BorderLayout.CENTER);
        cp.add(headPanel, BorderLayout.NORTH);

        loadSubjects(getyear(currentuser));
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == comboBox) {
            loadSubjects((String) comboBox.getSelectedItem());
        }

        if (e.getSource() == add) {

            if (group.getSelection() != null) {
                String selectedData = group.getSelection().getActionCommand();
                String[] data = selectedData.split("\\|");

                String code = data[0];
                String subject = data[1];
                int weight = Integer.parseInt(data[2]);

                if (checkduplicatesubject(currentuser, getsubject())) {
                    showWarning("Subject Duplicate");
                    warning.setForeground(Color.red);
                    return;
                }

                int currentweight = countweight(getusername());
                if(currentweight + weight > 21) {
                    showWarning("More than 21 credits");
                    warning.setForeground(Color.red);
                    return ;
                }

                savesubject(currentuser, code, subject, weight, getyear(currentuser));

                loadSubjects(getyear(currentuser));

                showWarning("Add Complete");
                warning.setForeground(Color.GREEN);
            } else {
                showWarning("Please Choose Subject");
                warning.setForeground(Color.red);
            }

        }
    }

    public int countweight(String user) {
        int total = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("Lib/sub_regis.csv"))) {
            String s;
            while ((s = br.readLine()) != null) {
                String data[] = s.split(",");
                // โครงสร้างไฟล์: user, code, subject, weight, year
                if (data[0].equals(user)) {
                    total += Integer.parseInt(data[3].trim());
                }
            }
        } catch (Exception ew) {
            ew.printStackTrace();
        }
        return total;
    }

    public boolean checkduplicatesubject(String user, String subject) {
        try (BufferedReader br = new BufferedReader(new FileReader("Lib/sub_regis.csv"))) {
            String s;
            while ((s = br.readLine()) != null) {
                String data[] = s.split(",");

                if (data[0].equals(user) && data[2].equals(subject)) {
                    return true;
                }
            }
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        return false;
    }

    public void showWarning(String msg) {
        warning.setText(msg);
    }

    public void loadSubjects(String selectedYear) {
        listPanel.removeAll();
        group = new ButtonGroup();

        GridBagConstraints g = new GridBagConstraints();
        g.anchor = GridBagConstraints.WEST;
        int row = 0;

        head = new JLabel("Code | Subject | Weight");
        g.gridx = 0;
        g.gridy = 0;
        head.setFont(new Font("", Font.BOLD, 25));
        listPanel.add(head, g);

        weight = new JLabel("Weight");
        g.gridx = 1;
        g.gridy = 0;
        weight.setFont(new Font("", Font.BOLD, 20));
        g.insets = new Insets(0, 0, 0, 80);
        listPanel.add(weight, g);

        currentweight = new JLabel(countweight(getusername()) + "");
        g.gridx = 1;
        g.gridy = 1;
        currentweight.setFont(new Font("", Font.BOLD, 20));
        g.anchor = GridBagConstraints.CENTER;
        listPanel.add(currentweight, g);

        g.anchor = GridBagConstraints.WEST;

        try (BufferedReader br = new BufferedReader(new FileReader("Lib/subject.csv"))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                if (d.length >= 4 && d[3].trim().equals(selectedYear)) {
                    String code = d[0].trim();
                    String subject = d[1].trim();
                    String weight = d[2].trim();

                    g.gridy = row + 1;

                    g.gridx = 0;
                    g.weightx = 1;
                    radio = new JRadioButton(code + "|" + subject + "|" + weight);
                    radio.setActionCommand(code + "|" + subject + "|" + weight);
                    radio.setBackground(Color.WHITE);
                    radio.setFont(new Font("", Font.PLAIN, 25));
                    group.add(radio);
                    listPanel.add(radio, g);

                    row++;

                }
            }
            g.gridx = 0;
            g.gridy = row + 1;
            g.weighty = 1;
            listPanel.add(new JLabel(""), g);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        listPanel.revalidate();
        listPanel.repaint();
    }
    public void savesubject(String user, String code, String subject, int weight, String year) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Lib/sub_regis.csv", true))) {
            bw.write(user + "," + code + "," + subject + "," + weight + "," + year);
            bw.newLine();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }
    public String getusername() {
        return currentuser;
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
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return "";
    }

    public String getsubject() {
        if (group.getSelection() != null) {
            String selectedData = group.getSelection().getActionCommand();
            String[] data = selectedData.split("\\|");
            return data[1];
        }
        return null;
    }

    public String getyear(String user) {
        try (BufferedReader br = new BufferedReader(new FileReader("Lib/Nisit.csv"))) {
            br.readLine();
            String s;
            while ((s = br.readLine()) != null) {
                String data[] = s.split(",");

                if (data[0].equals(user)) {
                    return data[3];
                }

            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return "";
    }

    
}