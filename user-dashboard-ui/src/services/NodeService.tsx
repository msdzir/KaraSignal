
export class NodeService {

    static getTreeNodes() {
        return fetch('data/treenodes.json').then(res => res.json()).then(d => d.root);
    }

}
 