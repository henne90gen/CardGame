package main;

import javax.swing.*;

/**
 * Created by Henne on 5/20/2016.
 */
public class Statistics extends JPanel implements Runnable {

    private int m_moves;
    private long m_time;
    private Thread m_timer;
    private boolean m_running;
    private boolean m_paused;

    private JLabel m_timeLabel;
    private JLabel m_moveLabel;

    public Statistics() {
        m_moves = 0;
        m_time = 0;

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
        return m_time;
    }

    public synchronized long pauseTimer() {
        m_paused = true;
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
            updateTimeLabel();
            try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    private synchronized void updateTimeLabel() {
        long secondsLong = m_time / 1000000000;
        long minutesLong = 0;
        if (secondsLong > 59) {
            minutesLong = secondsLong / 60;
            secondsLong = secondsLong % 60;
        }
        String secondsString = new Long(secondsLong).toString() + "s";
        String minutesString = new Long(minutesLong).toString() + "min";
        String text = (minutesLong != 0)?(minutesString + " "):("");
        text += secondsString;
        m_timeLabel.setText(text);
    }
}
