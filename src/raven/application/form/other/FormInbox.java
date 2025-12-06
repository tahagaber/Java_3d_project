package raven.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Enhanced Inbox Panel - Dark/Light Mode Compatible
 * @author Raven
 */
public class FormInbox extends javax.swing.JPanel {

    private JPanel booksPanel;
    private JTextField searchField;
    private JComboBox<String> categoryFilter;
    private JComboBox<String> sortFilter;
    private JLabel resultsLabel;
    private List<Book> allBooks;
    private List<Book> filteredBooks;

    public FormInbox() {
        initSampleBooks();
        initComponents();
        displayBooks(allBooks);
    }

    private void initSampleBooks() {
        allBooks = new ArrayList<>();
        allBooks.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "Classic", "$12.99", "⭐ 4.5", true, 32, "raven/icon/png/Taha (3).jpg"));
        allBooks.add(new Book("1984", "George Orwell", "Fiction", "$14.99", "⭐ 4.7", true, 15, "path/to/1984.jpg"));
        allBooks.add(new Book("To Kill a Mockingbird", "Harper Lee", "Classic", "$11.99", "⭐ 4.8", false, 0, "path/to/mockingbird.jpg"));
        allBooks.add(new Book("Pride and Prejudice", "Jane Austen", "Romance", "$10.99", "⭐ 4.6", true, 28, "path/to/pride.jpg"));
        allBooks.add(new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy", "$15.99", "⭐ 4.9", true, 45, "path/to/hobbit.jpg"));
        allBooks.add(new Book("Harry Potter", "J.K. Rowling", "Fantasy", "$13.99", "⭐ 4.8", true, 60, "path/to/harry.jpg"));
        allBooks.add(new Book("The Catcher in the Rye", "J.D. Salinger", "Fiction", "$12.49", "⭐ 4.3", true, 12, "path/to/catcher.jpg"));
        allBooks.add(new Book("Brave New World", "Aldous Huxley", "Science Fiction", "$13.49", "⭐ 4.5", true, 20, "path/to/brave.jpg"));
        allBooks.add(new Book("Animal Farm", "George Orwell", "Classic", "$9.99", "⭐ 4.6", false, 0, "path/to/animal.jpg"));
        allBooks.add(new Book("Jane Eyre", "Charlotte Brontë", "Romance", "$11.49", "⭐ 4.5", true, 18, "path/to/jane.jpg"));
        allBooks.add(new Book("Dune", "Frank Herbert", "Science Fiction", "$16.99", "⭐ 4.7", true, 35, "path/to/dune.jpg"));
        allBooks.add(new Book("The Alchemist", "Paulo Coelho", "Fiction", "$14.49", "⭐ 4.8", true, 50, "path/to/alchemist.jpg"));
        filteredBooks = new ArrayList<>(allBooks);
    }

    private void displayBooks(List<Book> books) {
        booksPanel.removeAll();
        filteredBooks = books;

        for (Book book : books) {
            booksPanel.add(createBookCard(book));
        }

        resultsLabel.setText(books.size() + " books found");
        booksPanel.revalidate();
        booksPanel.repaint();
    }

    private JPanel createBookCard(Book book) {
        JPanel card = new JPanel(new BorderLayout(12, 12));
        card.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        card.setPreferredSize(new Dimension(280, 360));
        card.putClientProperty(FlatClientProperties.STYLE,
                "arc:15;" +
                        "background:darken($Panel.background,3%)");

        // Cover with badge overlay - Full width
        JPanel coverContainer = new JPanel(null);
        coverContainer.setPreferredSize(new Dimension(280, 200));
        coverContainer.setOpaque(false);

        // Cover Image - Full width
        JLabel coverLabel = new JLabel();
        coverLabel.setPreferredSize(new Dimension(280, 200));
        coverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        coverLabel.setVerticalAlignment(SwingConstants.CENTER);
        coverLabel.setOpaque(true);
        coverLabel.putClientProperty(FlatClientProperties.STYLE,
                "background:darken($Panel.background,6%)");

        // Try to load image, fallback to placeholder
        try {
            ImageIcon icon = new ImageIcon(book.imagePath);
            if (icon.getIconWidth() > 0) {
                // Scale image to fit width while maintaining aspect ratio
                Image img = icon.getImage();
                Image scaledImg = img.getScaledInstance(280, 200, Image.SCALE_SMOOTH);
                coverLabel.setIcon(new ImageIcon(scaledImg));
            } else {
                // Fallback to placeholder
                coverLabel.setText("📖");
                coverLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
            }
        } catch (Exception e) {
            // Fallback to placeholder
            coverLabel.setText("📖");
            coverLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
        }

        coverContainer.add(coverLabel);

        // Stock/Bestseller Badge
        if (!book.inStock) {
            JLabel outOfStock = new JLabel(" Out of Stock ");
            outOfStock.setBounds(10, 10, 100, 25);
            outOfStock.setFont(new Font("Segoe UI", Font.BOLD, 10));
            outOfStock.setForeground(Color.WHITE);
            outOfStock.setBackground(new Color(220, 53, 69));
            outOfStock.setOpaque(true);
            outOfStock.setHorizontalAlignment(SwingConstants.CENTER);
            outOfStock.putClientProperty(FlatClientProperties.STYLE, "arc:5");
            coverContainer.add(outOfStock);
        } else if (book.stock > 40) {
            JLabel bestseller = new JLabel(" 🔥 Hot ");
            bestseller.setBounds(10, 10, 70, 25);
            bestseller.setFont(new Font("Segoe UI", Font.BOLD, 10));
            bestseller.setForeground(Color.WHITE);
            bestseller.setBackground(new Color(255, 87, 34));
            bestseller.setOpaque(true);
            bestseller.setHorizontalAlignment(SwingConstants.CENTER);
            bestseller.putClientProperty(FlatClientProperties.STYLE, "arc:5");
            coverContainer.add(bestseller);
        }

        // Info Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JLabel titleLabel = new JLabel(book.title);
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +1");
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel authorLabel = new JLabel(book.author);
        authorLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:$Label.disabledForeground");
        authorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        authorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel categoryLabel = new JLabel("📚 " + book.category);
        categoryLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        categoryLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:$Label.disabledForeground");
        categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel ratingLabel = new JLabel(book.rating);
        ratingLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
        ratingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(titleLabel);
        infoPanel.add(Box.createVerticalStrut(4));
        infoPanel.add(authorLabel);
        infoPanel.add(Box.createVerticalStrut(6));
        infoPanel.add(categoryLabel);
        infoPanel.add(Box.createVerticalStrut(4));
        infoPanel.add(ratingLabel);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new BorderLayout(8, 0));
        bottomPanel.setOpaque(false);

        JLabel priceLabel = new JLabel(book.price);
        priceLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +2;foreground:#2196F3");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        buttonPanel.setOpaque(false);

        JButton viewBtn = new JButton("View");
        viewBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        viewBtn.setFocusPainted(false);
        viewBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewBtn.putClientProperty(FlatClientProperties.STYLE,
                "arc:8;" +
                        "background:@accentColor;" +
                        "foreground:#ffffff;" +
                        "borderWidth:0");
        viewBtn.addActionListener(e -> onViewBook(book));

        JButton cartBtn = new JButton("🛒");
        cartBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        cartBtn.setPreferredSize(new Dimension(35, 28));
        cartBtn.setFocusPainted(false);
        cartBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cartBtn.putClientProperty(FlatClientProperties.STYLE, "arc:8");
        cartBtn.addActionListener(e -> onAddToCart(book));
        cartBtn.setEnabled(book.inStock);

        buttonPanel.add(viewBtn);
        buttonPanel.add(cartBtn);

        bottomPanel.add(priceLabel, BorderLayout.WEST);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        card.add(coverContainer, BorderLayout.NORTH);
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(bottomPanel, BorderLayout.SOUTH);

        return card;
    }

    private void onViewBook(Book book) {
        JOptionPane.showMessageDialog(this,
                "Book: " + book.title + "\n" +
                        "Author: " + book.author + "\n" +
                        "Category: " + book.category + "\n" +
                        "Price: " + book.price + "\n" +
                        "Rating: " + book.rating + "\n" +
                        "Stock: " + (book.inStock ? book.stock + " available" : "Out of stock"),
                "Book Details",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void onAddToCart(Book book) {
        if (book.inStock) {
            JOptionPane.showMessageDialog(this,
                    "Added \"" + book.title + "\" to cart! 🛒",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void onSearch() {
        String query = searchField.getText().toLowerCase().trim();
        String category = (String) categoryFilter.getSelectedItem();

        List<Book> filtered = new ArrayList<>();

        for (Book book : allBooks) {
            boolean matchesSearch = query.isEmpty() ||
                    book.title.toLowerCase().contains(query) ||
                    book.author.toLowerCase().contains(query);

            boolean matchesCategory = category.equals("All Categories") ||
                    book.category.equals(category);

            if (matchesSearch && matchesCategory) {
                filtered.add(book);
            }
        }

        displayBooks(filtered);
    }

    private void onSort() {
        String sortBy = (String) sortFilter.getSelectedItem();
        List<Book> toSort = new ArrayList<>(filteredBooks);

        switch (sortBy) {
            case "Price: Low to High":
                toSort.sort((a, b) -> {
                    double priceA = Double.parseDouble(a.price.replace("$", ""));
                    double priceB = Double.parseDouble(b.price.replace("$", ""));
                    return Double.compare(priceA, priceB);
                });
                break;
            case "Price: High to Low":
                toSort.sort((a, b) -> {
                    double priceA = Double.parseDouble(a.price.replace("$", ""));
                    double priceB = Double.parseDouble(b.price.replace("$", ""));
                    return Double.compare(priceB, priceA);
                });
                break;
            case "Rating: High to Low":
                toSort.sort((a, b) -> {
                    double ratingA = Double.parseDouble(a.rating.split(" ")[1]);
                    double ratingB = Double.parseDouble(b.rating.split(" ")[1]);
                    return Double.compare(ratingB, ratingA);
                });
                break;
            case "Name: A to Z":
                toSort.sort((a, b) -> a.title.compareToIgnoreCase(b.title));
                break;
        }

        displayBooks(toSort);
    }

    private void onClearFilters() {
        searchField.setText("");
        categoryFilter.setSelectedIndex(0);
        sortFilter.setSelectedIndex(0);
        displayBooks(allBooks);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(25, 25, 25, 25));

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout(15, 15));

        // Title and Stats
        JPanel titlePanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("📚 Browse Books");
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");

        resultsLabel = new JLabel(allBooks.size() + " books found");
        resultsLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:$Label.disabledForeground");

        titlePanel.add(titleLabel, BorderLayout.WEST);
        titlePanel.add(resultsLabel, BorderLayout.EAST);

        // Search Bar
        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));

        searchField = new JTextField();
        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search books or authors...");
        searchField.putClientProperty(FlatClientProperties.STYLE,
                "arc:10;" +
                        "margin:8,12,8,12");
        searchField.setPreferredSize(new Dimension(0, 40));
        searchField.addActionListener(e -> onSearch());

        JButton searchBtn = new JButton("🔍");
        searchBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        searchBtn.setPreferredSize(new Dimension(45, 40));
        searchBtn.setFocusPainted(false);
        searchBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchBtn.putClientProperty(FlatClientProperties.STYLE,
                "arc:10;" +
                        "background:@accentColor;" +
                        "foreground:#ffffff;" +
                        "borderWidth:0");
        searchBtn.addActionListener(e -> onSearch());

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchBtn, BorderLayout.EAST);

        // Filters Panel
        JPanel filtersPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));

        JLabel filterLabel = new JLabel("Filters:");
        filterLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));

        categoryFilter = new JComboBox<>(new String[]{
                "All Categories", "Classic", "Fiction", "Fantasy",
                "Romance", "Science Fiction"
        });
        categoryFilter.putClientProperty(FlatClientProperties.STYLE, "arc:8");
        categoryFilter.addActionListener(e -> onSearch());

        sortFilter = new JComboBox<>(new String[]{
                "Sort by: Default",
                "Price: Low to High",
                "Price: High to Low",
                "Rating: High to Low",
                "Name: A to Z"
        });
        sortFilter.putClientProperty(FlatClientProperties.STYLE, "arc:8");
        sortFilter.addActionListener(e -> onSort());

        JButton clearBtn = new JButton("✕ Clear");
        clearBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        clearBtn.setFocusPainted(false);
        clearBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearBtn.putClientProperty(FlatClientProperties.STYLE, "arc:8");
        clearBtn.addActionListener(e -> onClearFilters());

        JButton gridViewBtn = new JButton("▦");
        gridViewBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gridViewBtn.setPreferredSize(new Dimension(38, 32));
        gridViewBtn.setFocusPainted(false);
        gridViewBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gridViewBtn.putClientProperty(FlatClientProperties.STYLE,
                "arc:8;" +
                        "background:@accentColor;" +
                        "foreground:#ffffff;" +
                        "borderWidth:0");

        JButton listViewBtn = new JButton("☰");
        listViewBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        listViewBtn.setPreferredSize(new Dimension(38, 32));
        listViewBtn.setFocusPainted(false);
        listViewBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        listViewBtn.putClientProperty(FlatClientProperties.STYLE, "arc:8");

        filtersPanel.add(filterLabel);
        filtersPanel.add(categoryFilter);
        filtersPanel.add(sortFilter);
        filtersPanel.add(clearBtn);
        filtersPanel.add(Box.createHorizontalStrut(10));
        filtersPanel.add(new JLabel("View:"));
        filtersPanel.add(gridViewBtn);
        filtersPanel.add(listViewBtn);

        headerPanel.add(titlePanel, BorderLayout.NORTH);
        headerPanel.add(searchPanel, BorderLayout.CENTER);
        headerPanel.add(filtersPanel, BorderLayout.SOUTH);

        // Books Panel
        booksPanel = new JPanel(new GridLayout(0, 3, 20, 20));

        JScrollPane scrollPane = new JScrollPane(booksPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);

        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private static class Book {
        String title;
        String author;
        String category;
        String price;
        String rating;
        boolean inStock;
        int stock;
        String imagePath;

        Book(String title, String author, String category, String price, String rating, boolean inStock, int stock, String imagePath) {
            this.title = title;
            this.author = author;
            this.category = category;
            this.price = price;
            this.rating = rating;
            this.inStock = inStock;
            this.stock = stock;
            this.imagePath = imagePath;
        }
    }
}