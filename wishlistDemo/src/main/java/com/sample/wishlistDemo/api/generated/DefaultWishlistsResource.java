
package com.sample.wishlistDemo.api.generated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.sample.wishlistDemo.api.generated.WishlistItem;
import com.sample.wishlistDemo.api.generated.YaasAwareParameters;

/**
* Resource class containing the custom logic. Please put your logic here!
*/
@Component("apiWishlistsResource")
@Singleton
public class DefaultWishlistsResource implements com.sample.wishlistDemo.api.generated.WishlistsResource
{
	@javax.ws.rs.core.Context
	private javax.ws.rs.core.UriInfo uriInfo;

	/* GET / */
	@Override
	public Response get(final PagedParameters paged, final YaasAwareParameters yaasAware)
	{
		// place some logic here
		return Response.ok()
			.entity(new java.util.ArrayList<Wishlist>()).build();
	}

	/* POST / */
	@Override
	public Response post(final YaasAwareParameters yaasAware, final Wishlist wishlist)
	{
		// place some logic here
		return Response.created(uriInfo.getAbsolutePath())
			.build();
	}

	/* GET /{wishlistId} */
	@Override
	public Response getByWishlistId(final YaasAwareParameters yaasAware, final java.lang.String wishlistId)
	{
		// place some logic here
		
		String accessToken = "Bearer 022-902f2cc2-00b3-4bfa-9d38-eab3d8805d69";
		String baseUri = " https://api.beta.yaas.io/hybris/document/v1/";
		String tenant = "wishlistdemo025"; //yaasAware.getHybrisTenant();
		String client = "wishlistdemo025.client001"; //yaasAware.getHybrisClientId();
		String type = wishlistId;
		String fullUri = baseUri + tenant + "/" + client + "/data/" + type;
		String curlCommand = "/usr/bin/curl -i -H " + "\"Authorization: " + accessToken + "\" -X GET " + fullUri;
		System.out.println(curlCommand);
		
		// Build command 
        ArrayList<String> commands = new ArrayList<String>();
        commands.add("/bin/bash");
        commands.add("-c");
        commands.add(curlCommand);
		
        // Construct all items in the wishlist
     	ArrayList<WishlistItem> items = new ArrayList<WishlistItem>();
     		
        // Run the command to request document service
		try {
			ProcessBuilder pb = new ProcessBuilder(commands);
			pb.redirectErrorStream(true);
			Process process = pb.start();
			
			InputStream is = process.getInputStream();
		    InputStreamReader isr = new InputStreamReader(is);
		    BufferedReader br = new BufferedReader(isr);
		    String line;
		    
		    String product = null;
		    Integer amount = null;
		    String  date   = null;
		    
		    while ((line = br.readLine()) != null) {
		        System.out.println(line);

		        if (line.startsWith("  \"product\""))
		        {
					Pattern p = Pattern.compile("\"product\" : \"(\\w+)\"");
					Matcher m = p.matcher(line);
			        
			        if (m.find())
			        {
			        	product = new String(m.group(1));
			        }
		        }
		        
		        if (line.startsWith("  \"amount\""))
		        {
					Pattern p = Pattern.compile("\"amount\" : \"(\\d+)\"");
					Matcher m = p.matcher(line);
			        
			        if (m.find())
			        {
			        	amount = new Integer(m.group(1));
			        }
		        }
		        
		        if (line.startsWith("  \"createdAt\""))
		        {
					Pattern p = Pattern.compile("\"createdAt\" : \"(.+)\"");
					Matcher m = p.matcher(line);
			        
			        if (m.find())
			        {
			        	date = new String(m.group(1));
			        }
		        }
		        
		        if (product != null && amount != null && date != null)
		        {
		        	WishlistItem item = new WishlistItem();
		        	item.setProduct(product);
		        	item.setAmount(amount);
		        	item.setCreatedAt((date.equals("null"))? new Date() : new Date(date));
		        	items.add(item);
		            
		            product = null;
		        	amount  = null;
		        	date    = null;
		        }
		    }
		    
		    process.waitFor();
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     	
		for (WishlistItem item : items) {
    		
			// GET https://api.beta.yaas.io/hybris/productdetails/v2/{tenant}/productdetails/{productId}
        	//String productAccessToken = "Bearer 021-1d2bcd44-c174-4228-817d-9140b840d69a";
    		String productBaseUri = " https://api.beta.yaas.io/hybris/productdetails/v2/";
    		String hybrisTenant = "saphybriscaas"; //yaasAware.getHybrisTenant();
    		String productId = item.getProduct();
    		String productFullUri = productBaseUri + hybrisTenant + "/productdetails/" + productId;
    		//String getProductCommand = "/usr/bin/curl -i -H " + "\"Authorization: " + productAccessToken + "\" -X GET " + productFullUri;
    		String getProductCommand = "/usr/bin/curl -i " + " -X GET " + productFullUri;
    		System.out.println(getProductCommand);
    		
    		// Build command 
            ArrayList<String> getProductCommands = new ArrayList<String>();
            getProductCommands.add("/bin/bash");
            getProductCommands.add("-c");
            getProductCommands.add(getProductCommand);
            
            try {
	            ProcessBuilder pb = new ProcessBuilder(getProductCommands);
				pb.redirectErrorStream(true);
				Process process = pb.start();
				
				InputStream is = process.getInputStream();
			    InputStreamReader isr = new InputStreamReader(is);
			    BufferedReader br = new BufferedReader(isr);
			    String line;
			    
			    String productName = null;
			    Float productPrice = null;
			    
			    boolean productStarted = false;
			    boolean priceStarted   = false;
			    
			    while ((line = br.readLine()) != null) {
			    	
			    	System.out.println(line);
			    	
			    	if (line.startsWith("  \"product\""))
			    	{
			    		productStarted = true;
			    		continue;
			    	}
			    	if (productStarted && line.startsWith("      \"en\""))
			        {
						Pattern p = Pattern.compile("\"en\" : \"(.+)\"");
						Matcher m = p.matcher(line);
				        
				        if (m.find())
				        {
				        	productName = new String(m.group(1));
				        	item.setProduct(productName);
				        }
				        
				        productStarted = false;
				        continue;
			        }
			    	
			    	if (line.startsWith("  \"prices\""))
			    	{
			    		priceStarted = true;
			    		continue;
			    	}
			    	if (priceStarted && line.startsWith("    \"originalAmount\""))
			        {
						Pattern p = Pattern.compile("\"originalAmount\" : (.+),");
						Matcher m = p.matcher(line);
				        
				        if (m.find())
				        {
				        	productPrice = new Float(m.group(1));
				        	item.setNote(new Float(item.getAmount() * productPrice).toString());
				        }
				        
				        priceStarted = false;
				        continue;
			        }
				}
			    
			    try {
					process.waitFor();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		
		// Construct wishlist
		Wishlist list = new Wishlist();
		list.setId(wishlistId);
		list.setItems(items);
		
		return Response.ok()
			.entity(list).build();
	}

	/* PUT /{wishlistId} */
	@Override
	public Response putByWishlistId(final YaasAwareParameters yaasAware, final java.lang.String wishlistId, final Wishlist wishlist)
	{
		// place some logic here
		return Response.ok()
			.build();
	}

	/* DELETE /{wishlistId} */
	@Override
	public Response deleteByWishlistId(final YaasAwareParameters yaasAware, final java.lang.String wishlistId)
	{
		// place some logic here
		return Response.noContent()
			.build();
	}

	@Override
	public
	Response getByWishlistIdWishlistItems(final PagedParameters paged, 
			final YaasAwareParameters yaasAware,  final java.lang.String wishlistId)
	{
		// place some logic here
		return Response.ok()
				.entity(new java.util.ArrayList<WishlistItem>()).build();
	}

	@Override
	public
	Response postByWishlistIdWishlistItems(final YaasAwareParameters yaasAware,
			final java.lang.String wishlistId, final WishlistItem wishlistItem){
		// place some logic here
		
		String wishlistItemData = "\'{\"product\":\"" + wishlistItem.getProduct() + "\",\"amount\":\"" + wishlistItem.getAmount() + 
				"\",\"note\":\"" + wishlistItem.getNote() + "\", \"createdAt\":\"" + new Date().toString() + "\"}\'";
		String accessToken = "Bearer 022-902f2cc2-00b3-4bfa-9d38-eab3d8805d69";
		String baseUri = " https://api.beta.yaas.io/hybris/document/v1/";
		String tenant = "wishlistdemo025"; //yaasAware.getHybrisTenant();
		String client = "wishlistdemo025.client001"; //yaasAware.getHybrisClientId();
		String type = wishlistId;
		String fullUri = baseUri + tenant + "/" + client + "/data/" + type;
		String curlCommand = "/usr/bin/curl -i -H " + "\"Authorization: " + accessToken + "\" -H \"Content-type: application/json\" -X POST -d " + wishlistItemData + fullUri;
		System.out.println(curlCommand);
		
		// Build command 
        ArrayList<String> commands = new ArrayList<String>();
        commands.add("/bin/bash");
        commands.add("-c");
        commands.add(curlCommand);
		
        // Run the command to request document service
		try {
			ProcessBuilder pb = new ProcessBuilder(commands);
			pb.redirectErrorStream(true);
			Process process = pb.start();
			
			InputStream is = process.getInputStream();
		    InputStreamReader isr = new InputStreamReader(is);
		    BufferedReader br = new BufferedReader(isr);
		    String line;

		    while ((line = br.readLine()) != null) {
		        System.out.println(line);
		    }
		    
		    process.waitFor();
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Response.created(uriInfo.getAbsolutePath())
					.build();
	}

}
