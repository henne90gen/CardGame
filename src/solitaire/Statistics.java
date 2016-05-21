package solitaire;

import javax.swing.*;

/**
 * Created by Henne on 5/20/2016.
 */
public class Statistics implements Runnable {

    private int m_moves;
    private long m_time;
    private Thread m_timer;
    private boolean m_running;
    private boolean m_paused;

    private JLabel m_timeLabel;

    public Statistics() {
        m_moves = 0;
        m_time = 0;
        m_timeLabel = new JLabel();
        startTimer();
    }

    public int getMoves() {
        return m_moves;
    }

    public void addMove() {
        m_moves++;
    }

    public long getTime() {
        return m_time;
    }

    /**
     * Starts or restarts the m_timer
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
            m_timeLabel.setText(new Long(m_time / 100000000).toString());
            try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}
