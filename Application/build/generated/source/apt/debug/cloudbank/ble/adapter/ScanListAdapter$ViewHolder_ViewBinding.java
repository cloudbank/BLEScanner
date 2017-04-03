// Generated code from Butter Knife. Do not modify!
package cloudbank.ble.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import cloudbank.ble.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ScanListAdapter$ViewHolder_ViewBinding implements Unbinder {
  private ScanListAdapter.ViewHolder target;

  @UiThread
  public ScanListAdapter$ViewHolder_ViewBinding(ScanListAdapter.ViewHolder target, View source) {
    this.target = target;

    target.name = Utils.findRequiredViewAsType(source, R.id.device_name, "field 'name'", TextView.class);
    target.rssi = Utils.findRequiredViewAsType(source, R.id.rssi, "field 'rssi'", TextView.class);
    target.record = Utils.findRequiredViewAsType(source, R.id.record, "field 'record'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ScanListAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.name = null;
    target.rssi = null;
    target.record = null;
  }
}
