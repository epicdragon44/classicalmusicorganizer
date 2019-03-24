import com.mpatric.mp3agic.*;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GraphicsDriver extends JFrame {
    private JTextField field;
    private JButton button;
    public GraphicsDriver() {
        super("Classical Music Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setSize(1000, 1000);
        field = new JTextField();
        field.setBounds(100, 500, 500, 25);
        button = new JButton("Enter");
        button.setBounds(600, 500, 100, 25);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        this.add(field);
        this.add(button);
        field.setVisible(true);
        button.setVisible(true);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawString("Welcome to Classical Music Manager!", 100, 20);
        g.drawString("This simple program will guide you through setting up a large classical music library on your computer.", 100, 40);
        g.drawString("First, visit this link: https://tinyurl.com/yymwlyjh", 100, 70);
        g.drawString("Then, hit Download All in the top right corner. Depending on your internet speed, this may take a while.", 100, 100);
        g.drawString("Once it's downloaded, please extract it to a folder somewhere.", 100, 130);
        g.drawString("Please enter the directory where the files have been extracted to:",100, 160);
        g.drawString("Example: home/myUserName/Downloads/drive-download-20181121T222105Z-001/", 100, 190);


        String fileStr = field.getText();
        if (fileStr.equals("") || fileStr==null) {
            return;
        }

        g.drawString("Parsing...",100, 525);

        File[] files = new File(fileStr).listFiles();
        manipulate(files, 0, null, null);

        g.drawString("Your new music files have been saved to your home Music directory.", 100, 700);
        g.drawString("Tracks, albums, and artist data has been generated successfully for each.", 100, 750);
    }

    public static void manipulate(File[] files, int level, Track currentTrack, String currentAuthor) {
        for (File file : files) {
            if (file.isDirectory()) {
                if (level==0) {
                    manipulate(file.listFiles(), level + 1, new Track(file.getName()), null);
                } else if (level==1) {
                    manipulate(file.listFiles(), level + 1, currentTrack, file.getName());
                }
            } else {
                if (level==2) {
                    try {
                        Mp3File mp3file = new Mp3File(file.getAbsolutePath());
                        ID3v1 id3v1Tag;
                        if (mp3file.hasId3v1Tag()) {
                            id3v1Tag = mp3file.getId3v1Tag();
                        } else {
                            id3v1Tag = new ID3v1Tag();
                            mp3file.setId3v1Tag(id3v1Tag);
                        }
                        id3v1Tag.setTrack(currentTrack.getCountTrack()+"");
                        id3v1Tag.setArtist(currentAuthor);
                        id3v1Tag.setTitle(file.getName());
                        id3v1Tag.setAlbum(currentTrack.toString());
                        id3v1Tag.setGenre(32);
                        mp3file.save(FileSystemView.getFileSystemView().getHomeDirectory()+"/Music/"+file.getName());

                        currentTrack.incrementTrackCount();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (UnsupportedTagException e) {
                        e.printStackTrace();
                    } catch (InvalidDataException e) {
                        e.printStackTrace();
                    } catch (NotSupportedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new GraphicsDriver();
    }
}
