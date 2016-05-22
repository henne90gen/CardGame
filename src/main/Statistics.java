package main;

import javax.swing.*;
import java.io.*;

/**
 * Created by Henne on 5/20/2016.
 */
public class Statistics extends JPanel implements Runnable {

    private int m_moves;
    private long m_time;
    private Thread m_timer;
    private boolean m_running;
    private boolean m_paused;

    private int m_globalMoves;
    private long m_globalTime;
    private String m_fileName;

    private String m_gameName;

    private JLabel m_timeLabel;
    private JLabel m_moveLabel;

    public Statistics(String gameName) {
        m_gameName = gameName;
        m_fileName = m_gameName + ".stats";
        m_moves = 0;
        m_time = 0;

        readStatsFile();

        m_timeLabel = new JLabel();
        add(m_timeLabel);

        m_moveLabel = new JLabel(new Integer(m_moves).toString());
        add(m_moveLabel);

        startTimer();
    }

    public int getMoves() {
        return m_moves;
    }

    public void addMove() {
        m_moves++;
        m_moveLabel.setText(new Integer(m_moves).toString());
        updateStatsFile();
    }

    public long getTime() {
        return m_time;
    }

    /**
     * Starts or restarts the timer
     */
    public synchronized void startTimer() {
        if (m_timer != null && m_timer.isAlive()) {
            m_running = false;
        }
        m_timer = new Thread(this);
        m_timer.start();
    }

    public synchronized long stopTimer() {
        m_running = false;
        updateStatsFile();
        return m_time;
    }

    public synchronized long pauseTimer() {
        m_paused = true;
        updateStatsFile();
        return m_time;
    }

    public synchronized void resumeTimer() {
        m_paused = false;
    }

    public JLabel getTimerLabel() {
        return m_timeLabel;
    }

    @Override
    public void run() {
        long startTime = System.nanoTime();
        m_running = true;
        m_paused = false;
        while (m_running) {
            if (!m_paused) m_time = System.nanoTime() - startTime;
            m_timeLabel.setText(getTimeAsString(m_time));
            try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    public synchronized String getTimeAsString(long time) {
        long secondsLong = time / 1000000000;
        long minutesLong = 0;
        long hoursLong = 0;
        long daysLong = 0;
        if (secondsLong > 59) {
            minutesLong = secondsLong / 60;
            secondsLong = secondsLong % 60;
        }
        if (minutesLong > 59) {
            hoursLong = minutesLong / 60;
            minutesLong = minutesLong % 60;
        }
        if (hoursLong > 23) {
            daysLong = hoursLong / 24;
            hoursLong = hoursLong % 24;
        }
        String secondsString = new Long(secondsLong).toString() + "s";
        String minutesString = new Long(minutesLong).toString() + "min";
        String hoursString = new Long(hoursLong).toString() + "h";
        String daysString = new Long(daysLong).toString() + "d";
        String text = (daysLong != 0)?(daysString + " "):("");
        text += (hoursLong != 0)?(hoursString + " "):("");
        text += (minutesLong != 0)?(minutesString + " "):("");
        text += secondsString;
        return text;
    }

    public void readStatsFile() {
        try {
            if (!new File(m_fileName).exists()) resetStatsFile();
            BufferedReader reader = new BufferedReader(new FileReader(m_fileName));          // read current values
            m_globalTime = Long.parseLong(reader.readLine());
            m_globalMoves = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetStatsFile() {
        try {
            File statsFile = new File(m_fileName);
            statsFile.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(statsFile, false));
            writer.write("0" + "\n");
            writer.write("0");
            writer.flush();
            writer.close();
            m_globalTime = 0;
            m_globalMoves = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateStatsFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(m_fileName, false));      // override stats file
            long time = m_globalTime + m_time;
            int moves = m_globalMoves + m_moves;
            writer.write(new Long(time).toString() + "\n");
            writer.write(new Integer(moves).toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long getTotalTime() {
        return m_globalTime;
    }

    public int getTotalMoves() {
        return m_globalMoves;
    }
}
