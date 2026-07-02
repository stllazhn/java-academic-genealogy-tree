package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

public class PhDTreeTest {

    // These pre-defined Professor and PhDTree objects may be used to simplify the setup for your
    // test cases.  You are encouraged to add your own helper methods (even `tree3()` would be
    // considered "trivial", since no node has more than 1 child).
    private static final Professor prof1 = new Professor("1 Amy Huang", 2023);
    private static final Professor prof2 = new Professor("2 Maya Leong", 2023);
    private static final Professor prof3 = new Professor("3 Matthew Hui", 2025);
    private static final Professor prof4 = new Professor("4 Arianna Curillo", 2022);
    private static final Professor prof5 = new Professor("5 Michelle Gao", 2022);
    private static final Professor prof6 = new Professor("6 Isa Siu", 2024);
    private static final Professor prof7 = new Professor("7", 2024);

    private static final Professor prof8 = new Professor("8", 2024);

    private static final Professor prof9 = new Professor("9", 2024);

    private static final Professor prof10 = new Professor("10", 2024);

    // These helper methods create a copy of each Professor object, which would normally be seen as
    // wasteful.  They do so to help expose bugs involving the use of `==` instead of `.equals()`.
    private static PhDTree tree1() {
        return new PhDTree(new Professor(prof1));
    }

    private static PhDTree tree2() {
        return new PhDTree(new Professor(prof4));
    }

    private static PhDTree tree3() throws NotFound {
        PhDTree t = new PhDTree(new Professor(prof1));
        t.insert(prof1.name(), new Professor(prof2));
        t.insert(prof2.name(), new Professor(prof3));
        return t;
    }

    private static PhDTree tree4() throws NotFound {
        PhDTree t = new PhDTree(new Professor(prof1));
        t.insert(prof1.name(), new Professor(prof6));
        t.insert(prof1.name(), new Professor(prof2));
        t.insert(prof2.name(), new Professor(prof4));
        t.insert(prof2.name(), new Professor(prof5));
        t.insert(prof5.name(), new Professor(prof3));
        return t;
    }

    private static PhDTree tree5() throws NotFound {
        PhDTree t = new PhDTree(new Professor(prof1));
        t.insert(prof1.name(), new Professor(prof6));
        t.insert(prof1.name(), new Professor(prof7));
        t.insert(prof7.name(), new Professor(prof8));
        t.insert(prof7.name(), new Professor(prof9));
        t.insert(prof1.name(), new Professor(prof2));
        t.insert(prof2.name(), new Professor(prof4));
        t.insert(prof2.name(), new Professor(prof5));
        t.insert(prof5.name(), new Professor(prof3));
        return t;
    }

    @Test
    public void testConstructorProfToString() {
        PhDTree t1 = tree1();
        assertEquals("1 Amy Huang", t1.toString());
        assertEquals(prof1, t1.prof());

        PhDTree t2 = tree2();
        assertEquals("4 Arianna Curillo", t2.toString());
        assertEquals(prof4, t2.prof());
    }

    @Test
    public void testNumAdvisees() throws NotFound {
        PhDTree t = tree1();
        assertEquals(0, t.numAdvisees());

        PhDTree t2 = tree4();
        assertEquals(2, t2.numAdvisees());

        PhDTree t3 = tree5();
        assertEquals(3, t3.numAdvisees());
        Professor newProf = new Professor("Mr",2023);
        t3.insert("1 Amy Huang",newProf);
        assertEquals(4, t3.numAdvisees());

        PhDTree t4 = new PhDTree(prof10);
        assertEquals(0, t4.numAdvisees());
    }

    @Test
    public void testSize() throws NotFound {
        PhDTree t = tree3();
        assertEquals(3, t.size());

        PhDTree t2 = tree4();
        assertEquals(6, t2.size());

        PhDTree t3 = tree5();
        assertEquals(9, t3.size());

        PhDTree t4 = new PhDTree(prof10);
        assertEquals(1, t4.size());
        t4.insert("10",prof6);
        t4.insert("6 Isa Siu",prof7);
        assertEquals(3, t4.size());
        t4.insert("10",prof8);
        assertEquals(4, t4.size());
    }

    @Test
    public void testMaxDepth() throws NotFound {
        PhDTree t = tree3();
        assertEquals(3, t.maxDepth());

        PhDTree t3 = tree4();
        assertEquals(4, t3.maxDepth());

        PhDTree t4 = tree5();
        assertEquals(4, t4.maxDepth());

        PhDTree t5 = new PhDTree(prof10);
        Professor newProf = new Professor("Mr",2023);
        assertEquals(1, t5.maxDepth());
        t5.insert("10",newProf);
        assertEquals(2, t5.maxDepth());
        t5.insert("Mr",prof1);
        t5.insert("10",prof6);
        t5.insert("6 Isa Siu",prof7);
        assertEquals(3, t5.maxDepth());
        t5.insert("7",prof8);
        assertEquals(4, t5.maxDepth());
    }

    @Test
    public void testFindTree() throws NotFound {
        PhDTree tree1 = tree1();
        tree1.insert(prof1.name(), prof2);
        tree1.insert(prof2.name(), prof3);
        PhDTree tree4 = new PhDTree(prof2);
        tree4.insert(prof2.name(), prof3);
        assertEquals(tree4.prof(), tree1.findTree(prof2.name()).prof());
        assertEquals("2 Maya Leong[3 Matthew Hui]",
                tree1.findTree(prof2.name()).toString());

        assertThrows(NotFound.class, () -> tree2().findTree(prof5.name()));
        assertThrows(NotFound.class, () -> tree1.findTree(prof4.name()));
        assertEquals(1, tree1.findTree(prof3.name()).size());

        //first
        PhDTree t2 = tree4();
        t2.insert(prof4.name(), prof7);
        t2.insert(prof4.name(), prof8);
        t2.insert(prof4.name(), prof9);
        PhDTree subtree2 = tree2();
        subtree2.insert(prof4.name(), prof7);
        subtree2.insert(prof4.name(), prof8);
        subtree2.insert(prof4.name(), prof9);
        assertEquals(subtree2.prof(), t2.findTree(prof4.name()).prof());
        assertEquals("4 Arianna Curillo[7, 8, 9]", t2.findTree(prof4.name()).toString());

        assertThrows(NotFound.class, () -> tree4().findTree(prof8.name()));
        assertThrows(NotFound.class, () -> tree3().findTree(prof7.name()));
        assertEquals(4, t2.findTree(prof4.name()).size());

        //second
        PhDTree t3 = tree5();
        t3.insert(prof4.name(), prof10);
        PhDTree subtree3 = tree2();
        subtree3.insert(prof4.name(), prof10);
        assertEquals(subtree3.prof(), t3.findTree(prof4.name()).prof());
        assertEquals("4 Arianna Curillo[10]", t3.findTree(prof4.name()).toString());
        assertEquals(t3.prof(),t3.findTree(prof1.name()).prof());
        assertThrows(NotFound.class, () -> tree5().findTree(prof10.name()));
        assertThrows(NotFound.class, () -> tree3().findTree(prof10.name()));
        assertEquals(2, t3.findTree(prof4.name()).size());

        //third
        PhDTree t4 = new PhDTree(prof8);
        PhDTree subtree4 = new PhDTree(prof8);
        assertEquals(subtree4.prof(), t4.findTree(prof8.name()).prof());
        assertEquals("8", t4.findTree(prof8.name()).toString());
        assertThrows(NotFound.class, () -> tree1().findTree(prof9.name()));
        assertEquals(1, t4.findTree(prof8.name()).size());

        t4.insert(prof8.name(), prof2);
        t4.insert(prof2.name(), prof3);
        t4.insert(prof2.name(), prof4);
        PhDTree subtree4b = new PhDTree(prof2);
        subtree4b.insert(prof2.name(), prof3);
        subtree4b.insert(prof2.name(), prof4);
        assertEquals(subtree4b.prof(), t4.findTree(prof2.name()).prof());
        assertEquals("2 Maya Leong[4 Arianna Curillo, 3 Matthew Hui]",
                t4.findTree(prof2.name()).toString());

        assertThrows(NotFound.class, () -> tree2().findTree(prof6.name()));
        assertThrows(NotFound.class, () -> tree1.findTree(prof7.name()));
        assertEquals(3, t4.findTree(prof2.name()).size());
    }

    @Test
    public void containsTest() throws NotFound {
        PhDTree t = tree3();
        assertTrue(t.contains("1 Amy Huang"));
        assertFalse(t.contains(prof6.name()));
    }

    @Test
    public void testInsert() throws NotFound {
        PhDTree t = tree1();
        t.insert(prof1.name(), prof2);
        t.insert(prof2.name(), prof3);
        assertEquals("1 Amy Huang[2 Maya Leong[3 Matthew Hui]]", t.toString());

        PhDTree t2 = new PhDTree(prof2);
        t2.insert(prof2.name(), prof3);
        t2.insert(prof2.name(), prof4);
        t2.insert(prof3.name(), prof5);
        assertEquals("2 Maya Leong[4 Arianna Curillo, 3 Matthew Hui[5 Michelle Gao]]",
                t2.toString());
        assertThrows(NotFound.class, () -> t2.insert(prof6.name(), prof7));

        PhDTree t3 = new PhDTree(prof8);
        t3.insert(prof8.name(), prof5);
        assertEquals("8[5 Michelle Gao]", t3.toString());
        assertThrows(NotFound.class, () -> t3.insert(prof6.name(), prof7));

        PhDTree t4 = new PhDTree(prof3);
        t4.insert(prof3.name(), prof4);
        t4.insert(prof3.name(), prof5);
        t4.insert(prof4.name(), prof6);
        t4.insert(prof4.name(), prof7);
        t4.insert(prof7.name(), prof8);
        assertEquals("3 Matthew Hui[4 Arianna Curillo[6 Isa Siu, 7[8]], 5 Michelle Gao]",
                t4.toString());
        assertThrows(NotFound.class, () -> t4.insert(prof9.name(), prof10));
    }

    @Test
    public void testFindAdvisor() throws NotFound {
        PhDTree t = tree3();
        assertEquals(prof2, t.findAdvisor(prof3.name()));
        assertThrows(NotFound.class, () -> t.findAdvisor(prof1.name()));

        PhDTree t2 = tree4();
        assertEquals(prof5, t2.findAdvisor(prof3.name()));
        assertThrows(NotFound.class, () -> t2.findAdvisor(prof1.name()));
        assertThrows(NotFound.class, () -> tree1().findAdvisor(prof1.name()));

        PhDTree t3 = tree5();
        t3.insert(prof2.name(), prof10);
        assertEquals(prof2, t3.findAdvisor(prof10.name()));
        assertThrows(NotFound.class, () -> t3.findAdvisor(prof1.name()));

        PhDTree t4 = tree5();
        assertEquals(prof2, t4.findAdvisor(prof4.name()));
        assertThrows(NotFound.class, () -> t4.findAdvisor(prof10.name()));
    }

    @Test
    public void testFindAcademicLineage() throws NotFound {
        PhDTree t = tree3();
        List<Professor> lineage1 = new LinkedList<>();
        lineage1.add(prof1);
        lineage1.add(prof2);
        lineage1.add(prof3);
        assertEquals(lineage1, t.findAcademicLineage(prof3.name()));

        PhDTree t2 = tree4();
        List<Professor> lineage2 = new LinkedList<>();
        lineage2.add(prof1);
        lineage2.add(prof6);
        assertEquals(lineage2, t2.findAcademicLineage(prof6.name()));
        assertThrows(NotFound.class, () -> t2.findAdvisor(prof7.name()));

        PhDTree t3 = tree5();
        List<Professor> lineage3 = new LinkedList<>();
        lineage3.add(prof1);
        lineage3.add(prof2);
        lineage3.add(prof5);
        assertEquals(lineage3, t3.findAcademicLineage(prof5.name()));
        List<Professor> lineage3b = new LinkedList<>();
        lineage3b.add(prof1);
        lineage3b.add(prof7);
        lineage3b.add(prof9);
        assertEquals(lineage3b, t3.findAcademicLineage(prof9.name()));
        assertThrows(NotFound.class, () -> t3.findAdvisor(prof10.name()));
        Professor newProf = new Professor("proffers",2024);
        t3.insert("3 Matthew Hui", newProf);
        List<Professor> lineage5 = new LinkedList<>();
        lineage5.add(prof1);
        lineage5.add(prof2);
        lineage5.add(prof5);
        lineage5.add(prof3);
        lineage5.add(newProf);
        assertEquals(lineage5, t3.findAcademicLineage(newProf.name()));

        PhDTree t4 = new PhDTree(prof3);
        List<Professor> lineage4 = new LinkedList<>();
        lineage4.add(prof3);
        assertEquals(lineage4, t4.findAcademicLineage(prof3.name()));
        assertThrows(NotFound.class, () -> t4.findAdvisor(prof1.name()));
    }

    @Test
    public void testCommonAncestor() throws NotFound {
        PhDTree t = tree3();
        assertEquals(prof2, t.commonAncestor(prof2.name(), prof3.name()));
        assertEquals(prof1, t.commonAncestor(prof1.name(), prof3.name()));
        assertThrows(NotFound.class, () -> t.commonAncestor(prof5.name(), prof3.name()));

        PhDTree t2 = tree4();
        assertEquals(prof2, t2.commonAncestor(prof4.name(), prof3.name()));
        assertEquals(prof1, t2.commonAncestor(prof6.name(), prof4.name()));
        assertThrows(NotFound.class, () -> t2.commonAncestor(prof9.name(), prof3.name()));

        PhDTree t3 = new PhDTree(prof8);
        assertEquals(prof8, t3.commonAncestor(prof8.name(), prof8.name()));
        assertThrows(NotFound.class, () -> t3.commonAncestor(prof9.name(), prof1.name()));
        t3.insert(prof8.name(), prof9);
        assertEquals(prof8, t3.commonAncestor(prof8.name(), prof9.name()));
        assertEquals(prof9, t3.commonAncestor(prof9.name(), prof9.name()));
        assertThrows(NotFound.class, () -> t3.commonAncestor(prof9.name(), prof1.name()));

        PhDTree t4 = tree5();
        assertEquals(prof1, t4.commonAncestor(prof3.name(), prof6.name()));
        assertEquals(prof1, t4.commonAncestor(prof1.name(), prof4.name()));
        assertThrows(NotFound.class, () -> t4.commonAncestor(prof10.name(), prof1.name()));
    }

    @Test
    public void testPrintProfessors() throws NotFound {
        {  // Restrict scope to one test case
            PhDTree t = tree3();

            // A StringWriter lets us capture output that might normally be written to a file, or
            // printed on the console, in a String instead.
            StringWriter out = new StringWriter();

            // Need to wrap our Writer in a PrintWriter to satisfy `printProfessors()` (but we save
            // the original StringWriter so we can access its string later).  Flush the PrintWriter
            // when we are done with it.
            PrintWriter pw = new PrintWriter(out);
            t.printProfessors(pw);
            pw.flush();

            // Split string into lines for easy comparison ("\\R" is a "regular expression" that
            // matches both Windows and Unix line separators; it only works in methods like
            // `split()`).
            String[] lines = out.toString().split("\\R");
            String[] expected = {
                    "1 Amy Huang - 2023",
                    "2 Maya Leong - 2023",
                    "3 Matthew Hui - 2025"
            };
            assertArrayEquals(expected, lines);
        }

        // Feel free to define a helper method to avoid duplicated testing code.

        {
            PhDTree t2 = tree4();
            StringWriter out = new StringWriter();
            PrintWriter pw = new PrintWriter(out);
            t2.printProfessors(pw);
            pw.flush();
            String[] lines = out.toString().split("\\R");
            String[] expected = {
                    "1 Amy Huang - 2023",
                    "2 Maya Leong - 2023",
                    "4 Arianna Curillo - 2022",
                    "5 Michelle Gao - 2022",
                    "3 Matthew Hui - 2025",
                    "6 Isa Siu - 2024"
            };
            assertArrayEquals(lines, expected);
        }

        {
            PhDTree t3 = tree5();
            StringWriter out = new StringWriter();
            PrintWriter pw = new PrintWriter(out);
            t3.printProfessors(pw);
            pw.flush();
            String[] lines = out.toString().split("\\R");
            String[] expected = {
                    "1 Amy Huang - 2023",
                    "2 Maya Leong - 2023",
                    "4 Arianna Curillo - 2022",
                    "5 Michelle Gao - 2022",
                    "3 Matthew Hui - 2025",
                    "6 Isa Siu - 2024",
                    "7 - 2024",
                    "8 - 2024",
                    "9 - 2024"
            };
            assertArrayEquals(lines, expected);
        }

        {
            PhDTree t4 = new PhDTree(prof8);
            StringWriter out = new StringWriter();
            PrintWriter pw = new PrintWriter(out);
            t4.printProfessors(pw);
            pw.flush();
            String[] lines = out.toString().split("\\R");
            String[] expected = {
                    "8 - 2024"
            };
            assertArrayEquals(lines, expected);
        }
    }
}
