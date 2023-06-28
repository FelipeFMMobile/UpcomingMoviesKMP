//
//  ImageLoaderView.swift
//  UpComingMoviesKMM
//
//  Created by Felipe Menezes on 30/01/22.
//  Copyright © 2023 FMMobile. All rights reserved.
//

import SwiftUI

struct ImageLoaderView: View {
    let url: URL
    var body: some View {
        AsyncImage(url: url) { image in
            image.resizable()
        } placeholder: {
            ProgressView()
        }
    }
}

struct ImageLoaderView_Previews: PreviewProvider {
    static var previews: some SwiftUI.View {
        Group {
            ImageLoaderView(url:
                                URL(string: "https://image.tmdb.org/t/p/w185//pU3bnutJU91u3b4IeRPQTOP8jhV.jpg")!
            )
        }.previewLayout(.fixed(width: 150, height: 200))
    }
}
