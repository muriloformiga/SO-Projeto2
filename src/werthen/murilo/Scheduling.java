package werthen.murilo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public abstract class Scheduling {

	public static ArrayList<ProcessPage> mainMemory = new ArrayList<>();

	public static ArrayList<Process> ready = new ArrayList<>();
	public static ArrayList<Process> blocked = new ArrayList<>();
	public static ArrayList<Process> incoming = new ArrayList<>();
	public static Process running;

	protected int totalProcesses;
	public boolean changeProcess = true;
	private int countQuantum = 0;
	private int quantum;
	private int faults = 0;
	int inMemory = 0;

	protected int alpha;
	private int memoryCapacity;
	public long unitTime;

	protected Scanner scanFile1;
	protected Scanner scanFile2;

	private File inFile1 = new File("cenario1.txt");
	private File inFile2 = new File("referencias1.txt");
	private File outFile = new File("resultado-cenario1-30.txt");

	public Scheduling (int alpha, int quantum, int memoryCapacity) {
		this.memoryCapacity = memoryCapacity;
		this.quantum = quantum;
		this.alpha = alpha;
		this.unitTime = 1;
		this.totalProcesses = 0;
	}

	public void startScheduling () {
		try {
			scanFile1 = new Scanner(inFile1);
			scanFile2 = new Scanner(inFile2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		loadProcessFromFile();
		while (ready.size() + blocked.size() + incoming.size() > 0 || running != null) {
			decrementIncoming();
			decrementBlocked();
			prepareProcess();
			setPageOnMemory();
			executeProcess();
			exibe();
			unitTime++;
		}
		System.out.println("*** Total de Faltas: " + faults + " : " + inMemory + " ***");
		//saveProcessMetrics();
	}

	public void loadProcessFromFile () {

		while (scanFile1.hasNext()) {

			String linePage = scanFile2.nextLine().replace(",", " ").replace(":", " ").replace(";", " ");
			Scanner scanPage = new Scanner(linePage);
			int idPage = scanPage.nextInt();
			ArrayList<ProcessPage> processPages = new ArrayList<>();

			while (scanPage.hasNextInt()) {
				int time = scanPage.nextInt();
				int index = scanPage.nextInt();
				ProcessPage page = new ProcessPage(idPage + 1, time, index);
				processPages.add(page);
			}

			String lineProcess = scanFile1.nextLine().replace(",", " ");
			Scanner scanLine = new Scanner(lineProcess);
			Process process = new Process(scanLine.nextInt(),
					scanLine.nextInt(), scanLine.nextInt(), scanLine.nextInt(),
					scanLine.nextInt(), processPages);
			distributeProcess(process);
			totalProcesses++;
		}
	}

	protected void distributeProcess (Process p) {
		if (p.getSubmitionTime() > 0) {
			incoming.add(p);
		} else {
			if (numberOfProcesses() < alpha) {
				ready.add(p);
			} else {
				p.setSubmitionTime(p.getSubmitionTime() + 1);
			}
		}
	}

	public void prepareProcess () {
		if (countQuantum % quantum == 0 || changeProcess) {
			if (changeProcess) {
				countQuantum = 0;
				if (ready.size() > 0) {
					running = ready.get(0);
					ready.remove(0);
				} else {
					running = null;
				}
			} else {
				if (running != null) {
					ready.add(running);
				}
				changeProcess = true;
				if (ready.size() > 0) {
					running = ready.get(0);
					ready.remove(0);
				} else {
					running = null;
				}
			}
		}
		countQuantum++;
	}
	
	private boolean memoryContains (ProcessPage pp) {
		
		for (int i = 0; i < mainMemory.size(); i++) {
			if (mainMemory.get(i).getIdProcess() == pp.getIdProcess() &&
					mainMemory.get(i).getIndexPage() == pp.getIndexPage()) {
				return true;
			}
		}
		return false;
	}
	
	private void setPageOnMemory () {
		if (running != null) {
			for (int i = 0; i < running.getPages().size(); i++) {
				if (running.getPages().get(i).getTimePage() == 0) {
					running.getPages().get(i).setRefer(0);
					if (memoryContains(running.getPages().get(i))) {
						System.out.println("YES!");
						inMemory++;
						continue;
					} else if (mainMemory.size() < memoryCapacity) {
						mainMemory.add(running.getPages().get(i));
					} else {
						changePage(running.getPages().get(i));
						faults++;
					}
				}
				running.getPages().get(i).setTimePage(running.getPages().get(i).getTimePage() - 1);
			}
		}
	}

	public void changePage (ProcessPage pp) {
		
		// this method will be override by another
	}

	public void executeProcess () {
		for (int i = 0; i < mainMemory.size(); i++) {
			mainMemory.get(i).setRefer(mainMemory.get(i).getRefer() + 1);
		}
		if (running != null) {
			running.setServiceTime(running.getServiceTime() - 1);
			if (running.getServiceTime() == 0) {
				saveProcess(running);
				running = null;
				changeProcess = true;
			} else if (running.isTimeToBlock()) {
				blocked.add(running);
				running = null;
				changeProcess = true;
			} else {
				changeProcess = false;
			}
		}
	}

	public void decrementIncoming () {
		Iterator<Process> processIterator = incoming.iterator();
		while (processIterator.hasNext()) {
			Process process = processIterator.next();
			process.setSubmitionTime(process.getSubmitionTime() - 1);
			if (process.getSubmitionTime() == 0 ) {
				if (numberOfProcesses() < alpha) {
					processIterator.remove();
					ready.add(process);
				} else {
					process.setSubmitionTime(process.getSubmitionTime() + 1);
				}
			}
		}
	}

	public void decrementBlocked () {
		for (int i = 0; i < blocked.size(); i++) {
			blocked.get(i).setBlockTime(blocked.get(i).getBlockTime() - 1);
			blocked.get(i).setServiceTime(blocked.get(i).getServiceTime() - 1);
			if (blocked.get(i).getBlockTime() <= 0 || (blocked.get(i).getSecondTimeToBlock() != -1 &&
					blocked.get(i).getBlockTime() == blocked.get(i).getHalfBlockTime())) {
				ready.add(blocked.get(i));
				blocked.remove(blocked.get(i));
			}
		}
	}

	public int numberOfProcesses () {
		int run = 0;
		if (running != null) {
			run = 1;
		}
		return (ready.size() + blocked.size() + run);
	}

	public void saveProcess (Process p) {		      
		try { 
			if (!outFile.exists()) {            
				outFile.createNewFile();
			}
			FileWriter fw = new FileWriter(outFile, true); 
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write("----- PID: " + p.getPid() + " -----"); 
			bw.newLine();
			bw.write("Turnaround Time: ");
			bw.newLine();
			bw.write("Response Time: ");
			bw.newLine();
			bw.write("Waiting Time: ");
			bw.newLine();
			bw.newLine();
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	public void saveProcessMetrics () {
		try {
			if (!outFile.exists()) {            
				outFile.createNewFile();
			}
			FileWriter fw = new FileWriter(outFile, true); 
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write("-------------------- PROCESS SCHEDULING INFORMATIONS --------------------");
			bw.newLine();
			//bw.write("Avg. Turnaround Time: " + String.format("%.2f", totalTurnaroundTime / (float) totalProcesses));
			//bw.newLine();
			//bw.write("CPU Usage (%): " + String.format("%.2f", (CPUExecuting * 100.0) / unitTime));
			//bw.newLine();
			//bw.write("Avg. Response Time: " + String.format("%.2f", totalResponseTime / (float) totalProcesses));
			//bw.newLine();
			//bw.write("Avg. Waiting Time: " + String.format("%.2f", totalWaitingTime / (float) totalProcesses));
			//bw.newLine();
			bw.write("Simulation Time: " + unitTime);
			bw.newLine();
			bw.write("-------------------------------------------------------------------------");
			bw.newLine();
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 */

	public void exibe () {
		for (int i = 0; i < mainMemory.size(); i++) {
			System.out.println(i + ": " + mainMemory.get(i).getIdProcess() + " - " + mainMemory.get(i).getIndexPage() + " - " +
					mainMemory.get(i).getRefer());
		}
		System.out.println("");
	}
}
