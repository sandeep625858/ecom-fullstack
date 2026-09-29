import React from 'react';
import PageTitle from './PageTitle';

export default function PageHeading(props) {
  return (
    <div className="text-center max-w-[576px] mx-auto px-4 py-6">
      <PageTitle title={props.title}/>
      <p className="font-primary leading-6 text-gray-600 dark:text-lighter">{props.children}</p>
    </div>
  );
};
