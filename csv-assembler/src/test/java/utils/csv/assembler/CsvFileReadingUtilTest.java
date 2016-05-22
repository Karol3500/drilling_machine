package utils.csv.assembler;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CsvFileReadingUtilTest {

	CsvFileReadingUtil reader;

	@Before
	public void setup(){
		reader = new CsvFileReadingUtil();
	}

	@Test
	public void shouldReadAllLinesFromFile(){
		String filePath = "src/test/java/resources/multiLineFile";

		List<String> lines = reader.readFileLines(filePath);

		assertEquals("line1 line1 | line1", lines.get(0));
		assertEquals("line2", lines.get(1));
		assertEquals("line3", lines.get(2));
	}

	@Test
	public void shouldFindLineContainingText(){
		List<String> lines = Arrays.asList("elem1|elem3|elem3|elem4", "elem2.1|elem2.2");

		int index = reader.findLineWithText("elem2", lines);

		assertEquals(1, index);		
	}
	
	@Test
	public void shouldFindLinesContainingText(){
		List<String> lines = Arrays.asList("elem1|elem3|elem3|elem4", "elem2.1|elem2.2", "gfdg line ss");

		int index = reader.find2LinesWithTextAndGetSecondIndex("elem2", "line", lines);

		assertEquals(2, index);		
	}

	@Test
	public void shouldFindAlgorithmParameters(){
		List<String> lines = reader.readFileLines("src/test/java/resources/SimulatedAnnealing_a280.tsp_1.csv");

		List<String> parametersLines = reader.getLinesContainingAlgorithmParameters(lines);

		assertEquals("Algorithm parameters:", parametersLines.get(0));
		assertEquals("Number of cycles | 1", parametersLines.get(1));
		assertEquals("Start temperature | 500.0", parametersLines.get(2));
		assertEquals("Cooling coefficient | 0.95", parametersLines.get(3));
		assertEquals("Permutation attempts | 1", parametersLines.get(4));
		assertEquals(5,parametersLines.size());
	}

	@Test
	public void shouldFindAveragedResult(){
		List<String> lines = reader.readFileLines("src/test/java/resources/SimulatedAnnealing_a280.tsp_1.csv");

		List<String> avgResultLines = reader.getLinesContainingAveragedResult(lines);

		assertEquals("Averaged result:", avgResultLines.get(0));
		assertEquals("Best route length | Execution time[s] | Permutation", avgResultLines.get(1));
		String permutationLine = "37017.03603040412 | 0.1424 | 1 82 185 197 220 216 170 72 218 100 133 224 194 92 52 127 269 94 134 242 186 1 10 276 196 270 36 69 195 191 251 187 151 97 213 227 25 150 21 237 209 28 1 164 156 260 103 13 174 138 71 50 63 19 34 258 239 17 122 128 2 32 267 1 31 206 166 139 108 9 79 58 37 158 113 12 55 259 49 247 61 53 268 125 1 253 175 154 243 85 57 228 124 200 249 274 90 26 155 67 263 87 217 189 221 1 111 250 202 264 45 256 212 204 144 146 65 190 20 78 240 232 74 254 176 188 1 255 226 181 14 99 198 66 112 143 11 123 35 104 114 54 73 86 141 142 93 1 173 261 275 244 153 38 6 5 24 15 140 4 119 102 117 121 230 172 39 75 1 233 130 109 205 246 126 98 30 95 60 29 182 106 70 89 81 266 16 273 207 1 159 145 179 157 116 88 248 76 229 277 129 115 96 234 278 192 178 149 279 23 1 219 120 199 46 168 222 68 238 215 131 163 40 84 162 118 105 110 203 245 211 1 236 183 262 193 8 43 184 137 223 3 257 161 272 214 160 136 252 62 41 7 1 77 147 210 241 148 107 80 132 101 235 167 18 64 180 47 51 135 177 265 33 1 56 59 271 231 152 27 225 171 42 44 280 208 165 48 91 83 201 169 22 1";
		assertEquals(permutationLine, avgResultLines.get(2));
		assertEquals(3,avgResultLines.size());
	}
}
