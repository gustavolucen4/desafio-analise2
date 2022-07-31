package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import entities.Sale;

public class Program {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Locale.setDefault(Locale.US);
		Scanner keyBoard = new Scanner(System.in);
		
		System.out.print("Entre o caminho do arquivo: ");
		String path = keyBoard.next();
		
		List<Sale> listSale = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(new File(path)))){
			
			String line = br.readLine();
			while(line != null) {
				String[] strLine = line.split(",");
				listSale.add(new Sale(Integer.parseInt(strLine[0]), Integer.parseInt(strLine[1]), strLine[2], Integer.parseInt(strLine[3]), Double.parseDouble(strLine[4])));
				line = br.readLine();
			}
			
			System.out.println("Total de vendas por vendedor:");
			Set<String> names = listSale.stream()
					.map(x -> x.getSeller())
					.collect(Collectors.toSet());
			
			names.forEach(x -> {
				double total = listSale.stream()
						.filter(y -> y.getSeller().equals(x))
						.map(y -> y.getTotal())
						.reduce(0.0, (z,y) -> z + y);
				
				System.out.println(x + " - R$ " + String.format("%.2f", total));
			});
		}
		catch (FileNotFoundException e) {
			System.out.println("Erro: " + path + " (O sistema não pode encontrar o arquivo especificado)");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		keyBoard.close();
	}
}
