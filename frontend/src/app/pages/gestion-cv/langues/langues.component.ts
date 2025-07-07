import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Langue } from './langue.model';
import { LangueService } from './langue.service';

@Component({
  selector: 'app-langues',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './langues.component.html'
})
export class LanguesComponent implements OnInit {

  langues: Langue[] = [];
  editing?: Langue;                  // null -> pas de modal ; new -> création

  constructor(private langueSrv: LangueService) {}

  ngOnInit(): void { this.refresh(); }

  /* ---------- CRUD helpers ---------- */
  refresh() { this.langueSrv.list().subscribe(l => this.langues = l); }

  newLangue()                { this.editing = { idL: 0, codeL: '', titre: '' }; }
  edit(lang: Langue)         { this.editing = { ...lang }; }
  cancel()                   { this.editing = undefined; }

  save() {
    const obs = this.editing!.idL === 0
      ? this.langueSrv.add(this.editing!)
      : this.langueSrv.update(this.editing!);
    obs.subscribe(() => { this.cancel(); this.refresh(); });
  }

  delete(id: number) {
    if (confirm('Supprimer cette langue ?')) {
      this.langueSrv.remove(id).subscribe(() => this.refresh());
    }
  }
}
