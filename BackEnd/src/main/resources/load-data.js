'use strict';

const loadProducts = async function () {
  const results = await Promise.all(
      $('.grid-list .ut2-gl__item').get().map(async (element) => {
        const url = $(element).find(
            '.ut2-gl__body.content-on-hover .ut2-gl__image a').attr('href');
        const thumbnail = $(element).find('.ut2-gl__image img').attr('src');
        const title = $(element).find('.ut2-gl__content .ut2-gl__name a').attr(
            'title');
        let code, description, price;
        const productinfo = {};
        const images = [];
        const companyName = {};
        await $.ajax({
          url: url,
          type: 'get',
          success: function (data) {
            const $html = $(data);
            code = $html.find('meta[itemprop="sku"]').attr('content');
            description = $html.find('meta[itemprop="description"]').attr(
                'content');
            price = $html.find('meta[itemprop="price"]').attr('content');
            $html.find('meta[itemprop="image"]').each(function () {
              const imgUrl = $(this).attr('content');
              images.push(imgUrl);
            });

            $html.find('.ty-product-feature').each(function () {
              let featureLabel = $(this).find(
                  '.ty-product-feature__label span').text().trim();
              var text = ["Loại sản phẩm", "Kích thước", "Số trang", "Tác giả",
                "Nhà Xuất Bản"];
              let isValidFeature = true;
              switch (featureLabel) {
                case "Loại sản phẩm" :
                  featureLabel = "bookFormat";
                  break;
                case "Kích thước" :
                  featureLabel = "size";
                  break;
                case "Số trang" :
                  featureLabel = "numPage";
                  break;
                case "Tác giả" :
                  featureLabel = "author";
                  break;
                case "Nhà Xuất Bản" :
                  featureLabel = "PublishCompany";
                  break;
                default :
                  isValidFeature = false;
                  break;
              }
              const featureValue = $(this).find(
                  '.ty-product-feature__value').text().trim();
              if (featureLabel === "PublishCompany") {
                companyName.companyName = featureValue;
                productinfo[featureLabel] = companyName;
              } else if (isValidFeature) {
                productinfo[featureLabel] = featureValue;
              }
            });
          }
        });

        return {
          Books: {
            code: code,
            title: title,
            price: price,
            thumbnail: thumbnail,
            description: description,
            bookImages: images,
            BookDetails: productinfo
          }
        };
      })
  );

  console.log(JSON.stringify(results, null, 2));
};

loadProducts();
