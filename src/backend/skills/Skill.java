package backend.skills;

import backend.*;
import backend.marks.Cell;
import backend.marks.CellsData;
import frontend.frame.TableNoGaps;
import backend.skillsData.SkillEffect;

import java.io.IOException;
import java.util.Random;

public class Skill {

    public boolean isActive() {
        return true;
    }

    protected static boolean checkChance(double chance) {
        return Math.random() < chance;
    }

    protected static boolean checkChance(double chance, int row) {
        if (SmLightSkill.contains(row))
            return true;
        return checkChance(chance);
    }

    protected static boolean checkChance(double chance, Cell cell) {
        return checkChance(chance, cell.getRow());
    }

    protected static Integer getRandomRow() {
        Random rand = new Random();
        int row = rand.nextInt(CellsData.getRowCount()+ SmLightSkill.getLight().size());
        if (row >= CellsData.getRowCount())
            row = SmLightSkill.getLight().get(row - CellsData.getRowCount());
        return row;
    }

    protected static Cell getRandomCell() {
        Integer row = getRandomRow();
        return CellsData.getCell(row, new Random().nextInt(CellsData.getColumnCount()));
    }

    protected static Cell getRandomCanBiteCell() {
        Cell cell;
        int row = getRandomRow();
        int col;
        Random rand = new Random();
        do {
            col = rand.nextInt(CellsData.getColumnCount());
            cell = CellsData.getCell(row,col);
        } while (!cell.isCanBite());
        return cell;
    }

    protected int getAverageScore(int row) {
        Cell cell;
        double sum = 0;
        int count = 0;
        for (int i = 0; i < CellsData.getColumnCount(); i++){
            cell = CellsData.getCell(row,i);
            if (cell.isNumber()) {
                sum += cell.get();
                count++;
            }
        }
        return (int) Math.round((sum / count) - 0.0001);
    }

    public void begin() throws IOException {
    }

    protected static void startFon(int row, int col, String skillName) throws IOException {
        TableNoGaps.getSkillsPanel().addEffect(getStandardSizeEffect(new YX(row,col), skillName));
    }

    protected static void startFon(YX rowCol, String skillName) throws IOException {
        TableNoGaps.getSkillsPanel().addEffect(getStandardSizeEffect(rowCol, skillName));
    }

    protected static void startFon(YX rowCol, String skillName, int time) throws IOException {
        SkillEffect skillEffect = getStandardSizeEffect(rowCol, skillName);
        skillEffect.setTime(time);
        TableNoGaps.getSkillsPanel().addEffect(skillEffect);
    }

    protected static SkillEffect getStandardSizeEffect(YX rowCol, String skillName) throws IOException {
        int size = TableNoGaps.getCellSize();
        int y = rowCol.getY() * size;
        int x = rowCol.getX() * size;
        return new SkillEffect("image/skills/" + skillName + ".png",
                x,y,size*3,size*3);
    }

    protected static void startFon(YX rowCol, String skillName, int width, int height, boolean endlessly) throws IOException {
        int size = TableNoGaps.getCellSize();
        int y = rowCol.getY() * size + size;
        int x = rowCol.getX() * size + size;
        SkillEffect skillEffect = new SkillEffect("image/skills/" + skillName + ".png",
                x,y,size*width,size*height);
        if (endlessly)
            skillEffect.onEndlessly();
        TableNoGaps.getSkillsPanel().addEffect(skillEffect);
    }
}
