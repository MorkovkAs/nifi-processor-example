const template = document.createElement('template');
template.innerHTML = `
  <style>
  </style>
  
  <div>
  </div>
`;

class NameSelect extends HTMLElement {

    constructor() {
        super();

        this.attachShadow({mode: 'open'});

        this.shadowRoot.appendChild(template.content.cloneNode(true));
    }

    connectedCallback() {
        const me = this;

        this.setAttribute('process-id', document.getElementById('processId').innerHTML);
        this.setAttribute('client-id', document.getElementById('clientId').innerHTML);
        this.setAttribute('revision', document.getElementById('revision').innerHTML);

        this.addClickEvent('#saveContext', function () {
            me.saveContext();
        });

        this.loadContext().then((context) => {
            document.getElementById('nameInput').value = context.name;
            document.getElementById('result').innerHTML = 'Hello ' + context.name;
        });
    }

    addClickEvent (selector, fn) {
        $(document).on('click', selector, function (el) {
            fn(el)
        });
    }

    saveContext() {
        const me = this;

        const processorId = this.processId;
        const clientId = this.clientId;
        const revision = this.revision;
        const name = this.name;

        return new Promise((res, rej) => {
            return fetch(`api/criteria/set-context?name=${name}&processorId=${processorId}&clientId=${clientId}&revision=${revision}`)
                .then(data => {
                    me.showSuccessMessage('Спецификация успешно изменена');
                    res(data.json())
                })
                .catch((error) => {
                    me.showErrorMessage('Ошибка сохранения спецификации');
                    rej(error)
                });
        })
    }

    showSuccessMessage (msg) {
        $('#message')
            .removeClass()
            .addClass('success-message')
            .text(msg)
    }

    showErrorMessage (msg) {
        $('#message')
            .removeClass()
            .addClass('error-message')
            .text(msg)
    }


    loadContext() {
        const me = this;

        const processorId = this.processId;
        const clientId = this.clientId;
        const revision = this.revision;

        return new Promise((res, rej) => {
            return fetch(`api/criteria/get-context?processorId=${processorId}&clientId=${clientId}&revision=${revision}`)
                .then(data => res(data.json()))
                .catch((error) => {
                    me.showErrorMessage('Невозможно получить контекст процессора');
                    rej(error)
                });
        })
    }

    static get observedAttributes() {
        return ['process-id', 'client-id', 'revision'];
    }

    get processId() {
        return this.getAttribute('process-id');
    }

    get clientId() {
        return this.getAttribute('client-id');
    }

    get revision() {
        return this.getAttribute('revision');
    }

    get name() {
        return document.getElementById('nameInput').value
    }
}

window.customElements.define('name-select', NameSelect);
