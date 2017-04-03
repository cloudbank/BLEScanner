// Generated code from Butter Knife. Do not modify!
package cloudbank.ble.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import cloudbank.ble.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ScanListFragment_ViewBinding implements Unbinder {
  private ScanListFragment target;

  private View view2131492993;

  @UiThread
  public ScanListFragment_ViewBinding(final ScanListFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.scanButton, "field 'scanButton' and method 'onClick'");
    target.scanButton = Utils.castView(view, R.id.scanButton, "field 'scanButton'", Button.class);
    view2131492993 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick();
      }
    });
    target.pBar = Utils.findRequiredViewAsType(source, R.id.loading_spinner, "field 'pBar'", ProgressBar.class);
    target.rvDevices = Utils.findRequiredViewAsType(source, R.id.rvDevices, "field 'rvDevices'", RecyclerView.class);
    target.emptyView = Utils.findRequiredViewAsType(source, R.id.empty_view, "field 'emptyView'", TextView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ScanListFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.scanButton = null;
    target.pBar = null;
    target.rvDevices = null;
    target.emptyView = null;
    target.toolbar = null;

    view2131492993.setOnClickListener(null);
    view2131492993 = null;
  }
}
