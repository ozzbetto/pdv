/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Library;

import java.util.List;
import javax.swing.JLabel;

/**
 *
 * @author frodriguez
 */
public class Paginador<T> {

    private final List<T> _dataList;
    private final JLabel _label;
    private static int maxReg, _reg_por_pagina, pageCount, numPag = 1;

    public Paginador(List<T> dataList, JLabel label, int reg_por_pagina) {
        this._dataList = dataList;
        this._label = label;
        this._reg_por_pagina = reg_por_pagina;
        cargarDatos();
    }

    private void cargarDatos() {
        numPag = 1;
        maxReg = _dataList.size();
        pageCount = (maxReg / _reg_por_pagina);
        if ((maxReg % _reg_por_pagina) > 0) {
            pageCount += 1;
        }
        _label.setText("Página 1 de " + pageCount);
    }

    public int primero() {
        numPag = 1;
        _label.setText("Página " + numPag + " de" + pageCount);
        return numPag;
    }

    public int anterior() {
        if (numPag > 1) {
            numPag -= 1;
            _label.setText("Página " + numPag + " de" + pageCount);
        }
        return numPag;
    }

    public int siguiente() {
        if (numPag == pageCount) {
            numPag -= 1;
        }
        if (numPag < pageCount) {
            numPag += 1;
            _label.setText("Página " + numPag + " de" + pageCount);
        }
        return numPag;
    }
    
    public int ultimo() {
        numPag = pageCount;
        _label.setText("Página " + numPag + " de" + pageCount);
        return numPag;
    }
}
