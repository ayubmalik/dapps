import React, { useState, useEffect } from 'react';
import Web3 from 'web3';

interface PageProps {
  web3: Web3
}

function Page(props: PageProps) {
  const { web3 } = props;
  const [ethInfo, setEthInfo] = useState({ networkId: 0, name: '' });
  useEffect(() => {
    const all = Promise.all([
      web3.eth.net.getId(),
      web3.eth.net.getNetworkType(),
    ]);
    all.then((data) => {
      setEthInfo({
        networkId: data[0],
        name: data[1],
      });
    });
  }, []);
  return (
    <>
      <header>
        <p>Welcome to MLK Token </p>
      </header>
      <main>
        <section>
          <p>
            Network ID:
            {ethInfo.networkId}
          </p>
          <p>
            Network Name:
            {ethInfo.name}
          </p>
          <p>
            Current Account:
          </p>
        </section>
      </main>
    </>
  );
}

export default Page;
