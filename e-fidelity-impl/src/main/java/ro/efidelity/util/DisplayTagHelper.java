package ro.efidelity.util;

import java.util.Collections;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * Clasa pentru crearea unui {@link PageRequest} Spring Data din parametri
 * primiti de la un tabel DisplayTag.
 */
public class DisplayTagHelper {

	/**
	 * Creeaza un obiect {@link PageRequest} prin care se realizeaza paginarea
	 * cu Spring Data. Toate argumentele incep de la 1 si nu de la 0.
	 * 
	 * @param dtColumns
	 *            numele coloanelor dupa care se face sortarea, in ordinea din
	 *            tabel
	 * @param dtPageSize
	 *            dimensiunea unei pagini din tabel
	 * @param dtPage
	 *            numarul paginii din tabel
	 * @param dtOrder
	 *            ordinea sortarii datelor in tabel
	 * @param dtSortColumn
	 *            indexul din tabel al coloanei dupa care se sorteaza
	 * 
	 */
	public static PageRequest createPageRequest(String[] dtColumns,
			Integer dtPageSize, Integer dtPage, Integer dtOrder,
			Integer dtSortColumn) {

		if (dtPage == null || dtPage == 0)
			dtPage = 1;
		if (dtOrder == null)
			dtOrder = 1;
		if (dtSortColumn == null)
			dtSortColumn = 0;

		Direction direction = null;
		direction = (dtOrder == 1) ? Sort.Direction.ASC : Sort.Direction.DESC;
		Sort sortObj = (dtOrder == null) ? null : new Sort(direction,
				Collections.singletonList(dtColumns[dtSortColumn]));
		PageRequest pageable = new PageRequest(dtPage - 1, dtPageSize, sortObj);

		return pageable;
	}

	/**
	 * Creeaza un obiect {@link PageRequest} prin care se realizeaza paginarea
	 * cu Spring Data. Toate argumentele incep de la 1 si nu de la 0.
	 * 
	 * @param dtColumns
	 *            numele coloanelor dupa care se face sortarea, in ordinea din
	 *            tabel
	 * @param dtPageSize
	 *            dimensiunea unei pagini din tabel
	 * @param dtPage
	 *            numarul paginii din tabel
	 * @param dtOrder
	 *            ordinea sortarii datelor in tabel
	 * @param dtSortColumn
	 *            indexul din tabel al coloanei dupa care se sorteaza
	 * @param dtDefaultSortColumn
	 *            coloana implicita dupa care sunt sortate datele
	 * @param dtDefaultOrder
	 *            ordinea implicita dupa care se face sortarea
	 * 
	 */
	public static PageRequest createPageRequest(String[] dtColumns,
			Integer dtPageSize, Integer dtPage, Integer dtOrder,
			Integer dtSortColumn, Integer dtDefaultSortColumn,
			Integer dtDefaultOrder) {

		if (dtPage == null || dtPage == 0)
			dtPage = 1;
		if (dtOrder == null)
			dtOrder = dtDefaultOrder;
		if (dtSortColumn == null)
			dtSortColumn = dtDefaultSortColumn;

		Direction direction = null;
		direction = (dtOrder == 1) ? Sort.Direction.ASC : Sort.Direction.DESC;
		Sort sortObj = (dtOrder == null) ? null : new Sort(direction,
				Collections.singletonList(dtColumns[dtSortColumn]));
		PageRequest pageable = new PageRequest(dtPage - 1, dtPageSize, sortObj);

		return pageable;

	}

	/**
	 * Creeaza un obiect {@link PageRequest} prin care se realizeaza paginarea
	 * cu Spring Data. Toate argumentele incep de la 1 si nu de la 0.
	 * 
	 * @param dtColumns
	 *            numele coloanelor dupa care se face sortarea, in ordinea din
	 *            tabel
	 * @param dtPageSize
	 *            dimensiunea unei pagini din tabel
	 * @param dtPage
	 *            numarul paginii din tabel
	 * @param dtOrder
	 *            ordinea sortarii datelor in tabel
	 * @param dtSortColumn
	 *            indexul din tabel al coloanei dupa care se sorteaza
	 * @param dtDefaultPage
	 *            pagina implicita afisata la accesarea initiala a tabelului
	 * @param dtDefaultSortColumn
	 *            coloana implicita dupa care sunt sortate datele
	 * @param dtDefaultOrder
	 *            ordinea implicita dupa care se face sortarea
	 * 
	 */
	public static PageRequest createPageRequest(String[] dtColumns,
			Integer dtPageSize, Integer dtPage, Integer dtOrder,
			Integer dtSortColumn, Integer dtDefaultPage, Integer dtDefaultSort,
			Integer dtDefaultOrder) {

		if (dtPage == null || dtPage == 0)
			dtPage = dtDefaultPage;
		if (dtOrder == null)
			dtOrder = dtDefaultOrder;
		if (dtSortColumn == null)
			dtSortColumn = dtDefaultSort;

		Direction direction = null;
		direction = (dtOrder == 1) ? Sort.Direction.ASC : Sort.Direction.DESC;
		Sort sortObj = (dtOrder == null) ? null : new Sort(direction,
				Collections.singletonList(dtColumns[dtSortColumn]));
		PageRequest pageable = new PageRequest(dtPage - 1, dtPageSize, sortObj);

		return pageable;

	}

}
