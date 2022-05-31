import React, { useEffect, useState } from 'react';
import { useMoralis } from 'react-moralis';
import {
  Icon, LinkTo, Table, Typography,
} from 'web3uikit';

interface Item {
  domainName: string;
  expiryDate: string;
  updatedDate: string;
  isConfirmed: boolean;
  link: string
}

function EnsWatcher() {
  const { Moralis, isInitialized } = useMoralis();
  const [dataItems, setDataItems] = useState<Item[]>([]);

  useEffect(() => {
    async function liveQuery() {
      const query = new Moralis.Query('NameRegistered');
      const subscription = await query.subscribe();
      subscription.on('update', (obj) => {
        const {
          name, expires, confirmed, updatedAt,
        } = obj.attributes;
        const expiryDate = new Date(1000 * parseInt(expires, 10)).toLocaleDateString('en-GB');
        const updatedDate = new Date(updatedAt).toLocaleString('en-GB');
        const isConfirmed = confirmed;
        const link = `https://app.ens.domains/name/${name}.eth/details`;
        const item = {
          domainName: name, expiryDate, updatedDate, isConfirmed, link,
        };
        setDataItems((data) => [item, ...data.slice(0, 10)]);
      });
    }
    if (isInitialized) {
      liveQuery();
    }
  }, [isInitialized]);
  return (
    <div className="ensWatch">
      <h1>Name Registered Events</h1>

      <Table
        columnsConfig="5fr 2fr 1fr 2fr"
        data={dataItems.map((item) => [
          <LinkTo address={item.link} key={item.link} text={item.domainName} />,
          <Typography>{item.expiryDate}</Typography>,
          <Icon fill={item.isConfirmed ? 'green' : 'red'} size={16} svg={item.isConfirmed ? 'checkmark' : 'x'} />,
          <Typography>{item.updatedDate}</Typography>,
        ])}
        header={[
          <span>Name</span>,
          <span>Expires</span>,
          <span>Confirmed</span>,
          <span>Updated</span>,
        ]}
        pageSize={50}
        noPagination
      />
    </div>
  );
}

export default EnsWatcher;
