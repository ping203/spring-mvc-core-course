package guru.springframework.springmvccourse.controllers;

import guru.springframework.springmvccourse.domain.Product;
import guru.springframework.springmvccourse.services.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by yriyMitsiuk on 31.05.2018.
 */
public class ProductControllerTest {

    @Mock //Mockito Mock object
    private ProductService productService;

    @InjectMocks //setups up controller, and injects mock objects into it.
    private ProductController productController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void testGetAll() throws Exception {
        List<Product> products = Stream.generate(Product::new).limit(2).collect(Collectors.toList());

        //specific Mockito interaction, tell stub to return list of products
        when(productService.getAll()).thenReturn((List)products);

        mockMvc.perform(get("/product/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("product/products"))
                .andExpect(model().attribute("products", hasSize(2)));
    }

    @Test
    public void testGet() throws Exception {
        Integer id = 1;

        //Tell Mockito stub to return new product for ID 1
        when(productService.get(id)).thenReturn(new Product());

        mockMvc.perform(get("/product/show/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("product/show"))
                .andExpect(model().attribute("product", instanceOf(Product.class)));
    }

    @Test
    public void testCreate() throws Exception {

        //should not call service
        verifyZeroInteractions(productService);

        mockMvc.perform(get("/product/new"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("product/productform"))
                .andExpect(model().attribute("product", instanceOf(Product.class)));
    }

    @Test
    public void testEdit() throws Exception {
        Integer id = 1;

        //Tell Mockito stub to return new product for ID 1
        when(productService.get(id)).thenReturn(new Product());

        mockMvc.perform(get("/product/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/productform"))
                .andExpect(model().attribute("product", instanceOf(Product.class)));
    }

    @Test
    public void testDelete() throws Exception {
        Integer id = 1;

        mockMvc.perform(get("/product/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/product/products"));

        verify(productService, times(1)).delete(id);
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        Integer id = 1;
        String description = "Test Description";
        BigDecimal price = new BigDecimal("12.00");
        String imageUrl = "example.com";

        Product returnProduct = new Product();
        returnProduct.setId(id);
        returnProduct.setDescription(description);
        returnProduct.setPrice(price);
        returnProduct.setImageUrl(imageUrl);

        when(productService.saveOrUpdate(ArgumentMatchers.<Product>any())).thenReturn(returnProduct);

        mockMvc.perform(post("/product")
                .param("id", "1").param("description", "Test Description")
                .param("price", "12.00").param("imageUrl", "example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/product/show/1"))
                .andExpect(model().attribute("product", instanceOf(Product.class)))
                .andExpect(model().attribute("product", hasProperty("id", is(id))))
                .andExpect(model().attribute("product", hasProperty("description", is(description))))
                .andExpect(model().attribute("product", hasProperty("price", is(price))))
                .andExpect(model().attribute("product", hasProperty("imageUrl", is(imageUrl))))
                .andDo(print());

        //verify properties of bound object
        ArgumentCaptor<Product> boundProduct = ArgumentCaptor.forClass(Product.class);
        verify(productService).saveOrUpdate(boundProduct.capture());

        assertEquals(id, boundProduct.getValue().getId());
        assertEquals(description, boundProduct.getValue().getDescription());
        assertEquals(price, boundProduct.getValue().getPrice());
        assertEquals(imageUrl, boundProduct.getValue().getImageUrl());
    }
}