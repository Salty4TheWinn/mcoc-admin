/**
 * 
 */
package de.slux.mcoc.admin.ui.views.layouts;

/**
 * @author Slux
 *
 */
public class NodeCellPosition
{
    private int row;
    private int col;

    public NodeCellPosition(int row, int column) {
        this.row = row;
        this.col = column;
    }

    /**
     * @return the row
     */
    public int getRow()
    {
        return row;
    }

    /**
     * @param row
     *            the row to set
     */
    public void setRow(int row)
    {
        this.row = row;
    }

    /**
     * @return the col
     */
    public int getCol()
    {
        return col;
    }

    /**
     * @param col
     *            the col to set
     */
    public void setCol(int col)
    {
        this.col = col;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + col;
        result = prime * result + row;
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NodeCellPosition other = (NodeCellPosition) obj;
        if (col != other.col)
            return false;
        if (row != other.row)
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "NodeCellPosition [row=" + row + ", col=" + col + "]";
    }

}
