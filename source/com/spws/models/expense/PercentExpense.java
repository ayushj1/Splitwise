package com.spws.models.expense;

import com.spws.models.User;
import com.spws.models.split.PercentSplit;
import com.spws.models.split.Split;

import java.util.List;

public class PercentExpense extends Expense {
    public PercentExpense(double amount, User paidBy, List<Split> splits, ExpenseMetadata metadata) {
        super(amount, paidBy, splits, metadata);
    }

    @Override
    public boolean validate() {
        for(Split split : getSplits()) {
            if(!(split instanceof PercentSplit)) {
                return false;
            }
        }

        double totalPercent = 100;
        double sumSplitPercent = 0;
        for(Split split : getSplits()) {
            PercentSplit percentSplit = (PercentSplit) split;
            sumSplitPercent += percentSplit.getPercent();
        }

        if(totalPercent != sumSplitPercent) {
            return false;
        }
        
        return true;
    }
}