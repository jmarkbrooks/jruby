package org.jruby.compiler.ir.instructions;

import org.jruby.compiler.ir.IRScope;
import org.jruby.compiler.ir.Operation;
import org.jruby.compiler.ir.operands.MetaObject;
import org.jruby.compiler.ir.operands.Operand;
import org.jruby.compiler.ir.operands.Variable;
import org.jruby.compiler.ir.representations.InlinerInfo;

import java.util.Map;

// NOTE: the scopeOrObj operand can be a dynamic scope.
//
// The runtime method call that GET_CONST is translated to in this case will call
// a get_constant method on the scope meta-object which does the lookup of the constant table
// on the meta-object.  In the case of method & closures, the runtime method will delegate
// this call to the parent scope.
//
public class GET_CONST_Instr extends GetInstr
{
    public GET_CONST_Instr(Variable dest, IRScope scope, String constName)
    {
        super(Operation.GET_CONST, dest, new MetaObject(scope), constName);
    }

    public GET_CONST_Instr(Variable dest, Operand scopeOrObj, String constName)
    {
        super(Operation.GET_CONST, dest, scopeOrObj, constName);
    }

    public Operand simplifyAndGetResult(Map<Operand, Operand> valueMap)
    {
        simplifyOperands(valueMap);
        if (getSource() instanceof MetaObject) {
            IRScope s = ((MetaObject)getSource()).scope;
            return s.getConstantValue(getName());
        }
        else {
            return null;
        }
    }

    public Instr cloneForInlining(InlinerInfo ii) {
        return new GET_CONST_Instr(ii.getRenamedVariable(result), getSource().cloneForInlining(ii), getName());
    }
}
